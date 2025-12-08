# Refactor Phase

Apply a specific refactoring pattern: $ARGUMENTS

## Instructions

1. Confirm all tests are passing first
2. Apply the specified refactoring pattern
3. Run tests to verify behavior is unchanged
4. If tests fail, revert the change

## Common Refactoring Patterns

- `extract-method`: Extract code into a new method
- `rename`: Rename a variable, method, or class
- `inline`: Inline a method or variable
- `move`: Move code to a different location
- `extract-class`: Extract code into a new class
- `remove-duplication`: Eliminate duplicated code

## Usage Examples

- /refactor extract-method calculateTotal
- /refactor rename oldName to newName
- /refactor remove-duplication
