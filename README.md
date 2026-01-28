# Wordle Game

A Wordle-style game developed collaboratively as a team project.

---

## Repository Overview
This repository uses a **branch-based workflow** with issues, pull requests, and code reviews.

Direct pushes to `master` are restricted.

---

## Branching Strategy

- `master`
  - Stable, production-ready code only
- `feature/*`
  - New features (ex: `feature/keyboard-ui`)
- `fix/*`
  - Bug fixes (ex: `fix/duplicate-letter-logic`)

❌ Never push directly to `master`

---

## Workflow Overview

1. Create or assign an **Issue**
2. Create a **branch** from `master`
3. Make changes and commit (Before commiting make sure your branch is up to date and has a commit message) `git fetch` `git pull`
4. Open a **Pull Request**
5. Request review
6. Merge after approval

---

## Working With Issues

- Every feature or bug should have an Issue
- Issues should include:
  - Clear description
  - Acceptance criteria
  - Labels
  - Assignee

---

## Pull Requests

All changes must:
- Be linked to an Issue
- Pass review
- Be approved before merging

---

## 👀 Code Reviews

- At least **1 reviewer required**
- Reviewer checks:
  - Logic correctness
  - Code clarity
  - No breaking changes

---

## Local Development

```How to gain access to the repo and development
git clone <repo-url>
git checkout -b feature/your-branch-name


