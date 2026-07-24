#!/usr/bin/env python3
"""Check server logs for errors and crashes."""

import re
import sys
from pathlib import Path


def check_logs(log_dir="logs"):
    log_path = Path(log_dir)
    if not log_path.exists():
        print(f"Log directory {log_dir} not found")
        return 0

    error_patterns = [
        r"\[.*ERROR.*\]",
        r"\[.*FATAL.*\]",
        r"Exception in thread",
        r"Caused by:",
        r"java\.lang\.",
        r"crash",
        r"Crash",
    ]

    warning_patterns = [
        r"\[.*WARN.*\]",
        r"deprecated",
    ]

    errors = []
    warnings = []

    for log_file in log_path.glob("*.log"):
        with open(log_file) as f:
            for line_num, line in enumerate(f, 1):
                for pattern in error_patterns:
                    if re.search(pattern, line, re.IGNORECASE):
                        errors.append(f"{log_file}:{line_num}: {line.strip()}")
                        break
                else:
                    for pattern in warning_patterns:
                        if re.search(pattern, line, re.IGNORECASE):
                            warnings.append(f"{log_file}:{line_num}: {line.strip()}")
                            break

    if errors:
        print(f"Found {len(errors)} errors:")
        for e in errors[:20]:  # Limit output
            print(f"  {e}")
        if len(errors) > 20:
            print(f"  ... and {len(errors) - 20} more")
        return 1

    if warnings:
        print(f"Found {len(warnings)} warnings (non-fatal):")
        for w in warnings[:10]:
            print(f"  {w}")

    print("No errors found in logs")
    return 0


if __name__ == "__main__":
    sys.exit(check_logs())