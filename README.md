# Data Engineer Test

## MathService
This is a test of basic functional and coding skills. We will look for problem solving skills, code style and unit testing abilities.

The function `gcd()` in `MathService` is reported to fail with a `Failure` if no value is available in the `DataEnvironment`.
Please make sure this function fails in a proper way, similar to existing error handling.

## TopN
This is a test of abstract data engineering skills.

The function `findTopN(...)` in `TopN` is supposed to find the top N highest unique integers in a presumed endless stream of integers.
To process the `stream` list of `Int`, you can only hold a few values in memory at a given time. Therefore, a memory efficient way to process
this list is required.