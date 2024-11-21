from model.config import ChatHistory
from model import llama3 as _llama3
import time

llama3: lambda chat_history, user_prompt, *args: [] = _llama3.chat
token_streamer: lambda tokens, *args: [] = _llama3.token_streamer
chat_history = ChatHistory()


def init():
    print("Llama3 initialized")


def run_llama3(prompt: str, printer: callable = lambda x: print(x, end="", flush=True), reset: bool = False):
    if reset:
        global chat_history
        chat_history = ChatHistory()

    chunks = ""
    for chunk in token_streamer(*llama3(chat_history, prompt)):
        printer(chunk)
        chunks += chunk
        # time.sleep(0.1)

    printer("\n")
    chat_history.append("assistant", chunks)
    print("Done!")


run_llama3("안녕?")
chat_history = ChatHistory()
