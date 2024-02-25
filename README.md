# fred-data-control
personal data control practice


# delete config.json from past commits
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch hdfs-python/config.json" \
  --prune-empty --tag-name-filter cat -- --all