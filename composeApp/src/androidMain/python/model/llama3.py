from typing import Iterator

from llama_cpp import Llama, CreateChatCompletionStreamResponse
from .config import ChatHistory

# Set model id
# model_id = "Bllossom/llama-3.2-Korean-Bllossom-3B"
model_id = "lmstudio-community/Meta-Llama-3.1-8B-Instruct-GGUF"

# Load model
model = Llama.from_pretrained(
    repo_id=model_id,
    filename="*Q4_K_M.gguf",  # 4bit quantized model
    verbose=False
)

system_prompt = (
    "You are a Korean storyteller creating children's fairy tales. "
    "Story Format: PART 1 (opening + adventure + environmental issue) -> WAITING_FOR_PHOTO -> PART 2 (action + ending). "
    "Write in Korean with '~했습니다/했어요' endings. "
    "Each part: 250 characters, simple words for ages 7-10. "
    "Keep environmental issues simple and child-actionable. "
    "Always complete PART 1 before WAITING_FOR_PHOTO marker.\n\n"

    "Structure:\n"
    "1. Opening: 3-4 sentences introducing character\n"
    "2. Adventure: 3-4 sentences developing story (magical events/discoveries)\n"
    "3. Issue: 2-3 sentences about simple environmental problem\n"
    "4. Photo Action: 2-3 sentences using uploaded photo\n"
    "5. Ending: 2-3 sentences with positive message"
)
print("INFO: Use default system prompt -", system_prompt)


def chat(
        chat_history: ChatHistory, user_prompt: str, temperature=0.5, max_tokens=520, print_prompt=True
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

    return model.create_chat_completion(prompt, temperature=temperature, stream=True, max_tokens=530), print_prompt


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
