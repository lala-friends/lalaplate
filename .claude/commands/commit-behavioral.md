# Commit Behavioral Changes

Commit behavioral (functional) changes.

## Pre-commit Checklist

1. Verify ALL tests are passing
2. Verify ALL compiler/linter warnings are resolved
3. Verify changes include actual functionality (not just structural)
4. Verify the change is a single logical unit

## Instructions

1. Run tests to confirm passing
2. Run linter to check for warnings
3. Stage the behavioral changes
4. Create commit with message format:

```
feat|fix: [description of behavioral change]

- [what functionality was added/changed]
- [related test added]
```

## Examples

- `feat: add user authentication`
- `fix: correct calculation error in total`
- `feat: implement order validation`
