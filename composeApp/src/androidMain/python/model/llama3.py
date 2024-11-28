from typing import Iterator

from llama_cpp import Llama, CreateChatCompletionStreamResponse
from .config import ChatHistory

# Set model id
model_id = "lmstudio-community/Meta-Llama-3.1-8B-Instruct-GGUF"

# Load model
model = Llama.from_pretrained(
    repo_id=model_id,
    filename="*Q4_K_M.gguf",  # 4bit quantized model
    verbose=False
)

system_prompt = """
You are a helpful, smart, kind, and efficient AI Assistant & Storyteller who writes children's fairy tales.
You always fulfill the user's requests to the best of your ability.
You must answer only using in Korean language and keep the content ending with "~했어요".

Storyline: <PART 1> Opening, Crisis -> <USER ACTION> -> <PART 2> Action, Story Ending.
Crisis: Present specific environmental problems which are caused by human activities.
Each part: 350 characters (must complete a sentence), simple words for ages 7-10.
""".strip()
print("INFO: Use default system prompt -", system_prompt)


def chat(
        chat_history: ChatHistory, user_prompt: str, temperature=0.5, print_prompt=True
) -> tuple[
    Iterator[CreateChatCompletionStreamResponse], bool
]:
    """ Chatting interface """
    prompt = chat_history.create_prompt(system_prompt, user_prompt)
    chat_history.append("user", user_prompt)

    if print_prompt:
        print("PROMPT:")
        for line in prompt:
            print(line)
        print()

    return model.create_chat_completion(prompt, temperature=temperature, stream=True), print_prompt


def token_streamer(tokens: Iterator[CreateChatCompletionStreamResponse], print_prompt: bool = True) -> Iterator[str]:
    """ Token streamer """
    if print_prompt:
        print("ANSWER:")

    for token in tokens:
        delta: dict = token['choices'][0]['delta']
        token_delta = delta.get('content')
        if token_delta:
            print(token_delta, end="")
        yield token_delta if token_delta else ""
    print()
