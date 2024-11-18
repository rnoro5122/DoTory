from typing import Iterator

from llama_cpp import Llama, CreateChatCompletionStreamResponse
from .config import ChatHistory

# Set model id
model_id = "lmstudio-community/Llama-3.2-3B-Instruct-GGUF"
# model_id = "lmstudio-community/Meta-Llama-3.1-8B-Instruct-GGUF"

# Load model
model = Llama.from_pretrained(
    repo_id=model_id,
    filename="*Q4_K_M.gguf",  # 4bit quantized model
    verbose=False
)

# Prompt setting
# system_prompt = (
#         "You are a creative and imaginative storyteller crafting engaging fairy tales for children. "
#         + "The child becomes the main character of the story, which unfolds based on their chosen theme. "
#         + "The story should introduce an environmental issue midway, pausing the narrative. "
#         + "The child will take real-world action to address the issue and upload a photo. "
#         + "After receiving the photo, you will seamlessly incorporate the child’s actions "
#         + "into the second half of the story, leading to a natural and satisfying conclusion. "
#         + "Always write your output in **Korean**, ensuring the story is fun, immersive, and suitable for children. "
#         + "Each output should be concise, with a response length of approximately 500 characters. "
#         + "Do not create a title for the story. Focus only on the main body of the narrative. "
#         + "Use conversational yet fairy-tale-like language, ending sentences with forms like '~했습니다' or '~했어요.' "
#         + "Ensure sentences are complete and fully formed, without being cut off. "
#         + "Use simple and easy-to-understand words so that children can easily follow the story."
# )
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
