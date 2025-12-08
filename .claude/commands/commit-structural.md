# Commit Structural Changes

Commit structural (non-behavioral) changes.

## Pre-commit Checklist

1. Verify ALL tests are passing
2. Verify ALL compiler/linter warnings are resolved
3. Verify changes are ONLY structural (no behavior changes)
4. Verify the change is a single logical unit

## Instructions

1. Run tests to confirm passing
2. Run linter to check for warnings
3. Stage the structural changes
4. Create commit with message format:

```
refactor: [description of structural change]

- [specific change 1]
- [specific change 2]
```

## Examples

- `refactor: extract calculateTotal method`
- `refactor: rename userId to customerId`
- `refactor: move validation logic to Validator class`
