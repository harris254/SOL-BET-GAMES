#!/system/bin/sh
# AIDE-compatible Gradle launcher.
# AIDE's build server provides Gradle; this wrapper forwards all arguments to it.
if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
fi
if [ -x /usr/bin/gradle ]; then
  exec /usr/bin/gradle "$@"
fi
if [ -x /usr/local/bin/gradle ]; then
  exec /usr/local/bin/gradle "$@"
fi
echo "Gradle was not found in the AIDE build environment." >&2
exit 127
