import os
import subprocess
import openai
import requests

# 환경 변수
OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
GITHUB_REPOSITORY = os.getenv("GITHUB_REPOSITORY")
PR_NUMBER = os.getenv("PR_NUMBER")

PROMPT_TEMPLATE_FILE = os.getenv("PROMPT_TEMPLATE_FILE", "review_prompt_template.txt")
OUTPUT_FILE = "review_comment.txt"

openai.api_key = OPENAI_API_KEY

# PR 정보 가져오기
def get_pr_info(repo, pr_number, token):
    url = f"https://api.github.com/repos/{repo}/pulls/{pr_number}"
    headers = {"Authorization": f"token {token}"}
    res = requests.get(url, headers=headers)
    res.raise_for_status()
    data = res.json()
    return data["title"], data["body"]

# 코드 변경사항(diff) 가져오기
def get_diff():
    try:
        return subprocess.check_output(["git", "diff", "origin/main...HEAD"], text=True)
    except subprocess.CalledProcessError:
        print("❌ Unable to fetch diff with 'origin/main'. Ensure the 'main' branch exists and is up to date.")
        return ""

# 프롬프트 템플릿 로딩 후 변수 치환
def load_and_fill_prompt(pr_number, pr_title, pr_desc, diff_content):
    with open(PROMPT_TEMPLATE_FILE, "r", encoding="utf-8") as f:
        template = f.read()
    return template \
        .replace("{pr_number}", str(pr_number)) \
        .replace("{pr_title}", pr_title) \
        .replace("{pr_description}", pr_desc or "(설명 없음)") \
        .replace("{diff_content}", diff_content)

# GPT 리뷰 요청
def generate_review(prompt):
    response = openai.ChatCompletion.create(
        model="gpt-4o",
        messages=[
            {"role": "system", "content": "You are a senior software engineer reviewing code."},
            {"role": "user", "content": prompt}
        ],
        temperature=0.4
    )
    return response.choices[0].message.content.strip()

# 리뷰 저장
def save_to_file(content, filename):
    with open(filename, "w", encoding="utf-8") as f:
        f.write(content)

# PR 코멘트 등록
def post_comment(filename):
    try:
        result = subprocess.run(
            ["gh", "pr", "comment", "-F", filename],
            check=True,
            capture_output=True,
            text=True
        )
        print("✅ GitHub PR 코멘트 등록 성공:")
        print(result.stdout)
    except subprocess.CalledProcessError as e:
        print("❌ GitHub PR 코멘트 등록 실패:")
        print(e.stderr)

def check_env_vars():
    print("🔎 환경 변수 확인:")
    for key in ["OPENAI_API_KEY", "GITHUB_TOKEN", "GITHUB_REPOSITORY", "PR_NUMBER"]:
        val = os.getenv(key)
        print(f"{key}: {'✅ OK' if val else '❌ MISSING'}")

def main():
    check_env_vars()

    if not all([OPENAI_API_KEY, GITHUB_TOKEN, GITHUB_REPOSITORY, PR_NUMBER]):
        print("❌ 필수 환경 변수가 누락되었습니다.")
        return

    print("📦 PR 정보 가져오는 중...")
    title, desc = get_pr_info(GITHUB_REPOSITORY, PR_NUMBER, GITHUB_TOKEN)

    print("📝 변경된 코드 diff 추출 중...")
    diff = get_diff()

    print("📄 프롬프트 템플릿 로딩 및 치환 중...")
    prompt = load_and_fill_prompt(PR_NUMBER, title, desc, diff)

    print("🤖 GPT에게 리뷰 요청 중...")
    review = generate_review(prompt)

    print(f"💾 리뷰 결과를 '{OUTPUT_FILE}' 파일로 저장 중...")
    save_to_file(review, OUTPUT_FILE)

    print("📤 PR에 댓글 등록 중...")
    post_comment(OUTPUT_FILE)

    print("✅ 완료: 리뷰가 PR에 성공적으로 등록되었습니다!!!")

if __name__ == "__main__":
    main()