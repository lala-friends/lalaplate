# TDD Status

Show current TDD workflow status.

## Instructions

1. Read plan.md and show progress:
   - Completed tests (with checkmarks)
   - Next pending test
   - Remaining tests
2. Run tests and show current phase (RED/GREEN)
3. Check for any uncommitted changes
4. Recommend next action

## Output Format

```
## Plan Progress
- [x] Completed: X tests
- [ ] Next: [description of next test]
- [ ] Remaining: Y tests

## Current Phase
[RED/GREEN] - [explanation]

## Uncommitted Changes
[yes/no] - [summary if yes]

## Recommended Action
[/go | /green | /tidy | /commit-structural | /commit-behavioral]
```
