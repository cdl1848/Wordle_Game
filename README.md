# Wordle Game

A Wordle-style game developed collaboratively as a team project.

---

## Repository Overview
This repository uses a **branch-based workflow** with issues, pull requests, and code reviews.

Direct pushes to `master` are restricted.

---

### How to clone the project
1. Copy the repository URL from Github
2. Open a terminal
3. Run: change <> as needed
```bash
git clone <repository-url>
cd <project>
```

---

## Workflow Overview

1. Create or assign an **Issue**
2. Create a **branch** from `master`
3. Make changes and commit (Before commiting make sure your branch is up to date and has a commit message) `git fetch` `git pull`
4. Open a **Pull Request**
5. Request review
6. Merge after approval

---

### Creating Issues

- Every feature or bug should have an Issue
- Issues should include:
  - Clear Title
  - Clear description
  - How to replicate issue if any
  - Labels (Priority, weight, type, progress)
  - Assignee

---

### Going into Branch

- Create a branch for an issue to ensure changes are made localy
#### Steps to open a branch
1. Ensure your local `master` branch is up to date
```bash
git checkout master
git fetch
git pull origin master
```
2. Now copy and enter into your issues branch locally
```bash
git checkout <branch-name>
```
3. Ensure your branch is up to date constantly (This step is for a branch that has been up for a while)
  * First, fetch the latest version of `master`
```bash
git fetch origin
```
  * Then merge the lastest changes
```bash
git merge origin/master
```
4. It is also important to make sure you push your changes as it only effects your local branch until it is fixed

---

### Pull Requests

All changes must:
- Be linked to an Issue
- Pass review
- Be approved before merging

#### Once an issue is believed to be finished
1. Ensure your branch is up to date with `master`
2. Resolve any merge conflicts
3. Stage and commit your changes
4. Push your branch to GitHub
5. Open a Pull Request from your branch into `master`
6. Link the related issue in the PR description
7. Request a review
8. Wait for approval before merging

---

### Code Reviews

- At least **1 reviewer required**
- Reviewer checks for:
  - Logic correctness
  - Code clarity
  - No breaking changes
  - Commpletion of the issue requirements
  
#### Steps for reviewers
1. Open the pull request on GitHub
2. Review the code changes and confirm the issue has been resolved
3. Leave comments if changes are needed
4. Approve the pull request if everything looks correct
5. After approval, the pull request can be merged into `master` by a team member with permission

---
