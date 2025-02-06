import os
import re

# Base folder containing Markdown files (change this)
FOLDER_PATH = "/Users/siebemichiels/Documents/personal/TwoDark/docs"

# Regex to match Markdown headers
HEADER_PATTERN = re.compile(r"^(#{1,6})\s+(.+)", re.MULTILINE)
H1_PATTERN = re.compile(r"^(#\s+.+)\n", re.MULTILINE)  # Matches the first H1 title


def generate_anchor(text):
    """
    Converts a header text to a GitHub-style anchor link.
    Example: "My Header!" -> "my-header"
    """
    text = text.lower().strip()
    text = re.sub(r"[^\w\s-]", "", text)  # Remove special characters
    text = re.sub(r"\s+", "-", text)  # Replace spaces with dashes
    return text


def generate_toc(content):
    """
    Generates a Table of Contents (TOC) based on Markdown headers.
    """
    headers = HEADER_PATTERN.findall(content)
    if not headers:
        return ""

    toc_lines = ["## Table of Contents\n"]
    for level, title in headers[1:]:  # Skip the first H1 title
        if ("Contents" in title): continue

        indent = "    " * (len(level) - 2)  # Indent based on header level
        anchor = generate_anchor(title)
        toc_lines.append(f"{indent}- [{title}](#{anchor})")

    return "\n".join(toc_lines) + "\n"


def process_markdown_files(folder):
    """
    Recursively iterates through all Markdown files in the folder and subfolders,
    generates TOC, and inserts it after the first H1 title.
    """
    for root, _, files in os.walk(folder):  # Recursively walk through directories
        for filename in files:
            if filename.endswith(".md"):
                file_path = os.path.join(root, filename)

                with open(file_path, "r", encoding="utf-8") as file:
                    content = file.read()

                match = H1_PATTERN.search(content)
                if not match:
                    print(f"Skipping {filename}: No H1 title found.")
                    continue  # Skip files without a main title

                toc = generate_toc(content)

                # Remove existing TOC before inserting a new one
                content = re.sub(r"## Table of Contents\n.*?\n\n", "", content, flags=re.DOTALL)

                # Insert TOC after the first H1 title
                new_content = content.replace(match.group(0), match.group(0) + "\n" + toc, 1)

                with open(file_path, "w", encoding="utf-8") as file:
                    file.write(new_content)

                print(f"Updated TOC for: {file_path}")


if __name__ == "__main__":
    process_markdown_files(FOLDER_PATH)
    print("TOC generation complete for all Markdown files (including subdirectories).")
