#!/bin/bash

detect-secrets scan . \
               --all-files \
               --exclude-files '\.secrets..*' \
               --exclude-files '\.git.*' \
               --exclude-files '\.pre-commit-config\.yaml' \
               --exclude-files 'target' \
               --exclude-files 'docs/release/.*' \
               --exclude-files '\.vscode.*' > .secrets.baseline
