#!/bin/sh
# Adds CW Practice Desk to the app menu for the current user.
set -e
DIR="$(cd "$(dirname "$0")" && pwd)"
mkdir -p ~/.local/share/applications ~/.local/share/icons/hicolor/scalable/apps
cp "$DIR/cw-practice-desk.svg" ~/.local/share/icons/hicolor/scalable/apps/
cat > ~/.local/share/applications/cw-practice-desk.desktop <<EOD
[Desktop Entry]
Type=Application
Name=CW Practice Desk
Comment=Morse code trainer (Koch lessons, word drills)
Exec=/usr/bin/env python3 "$DIR/cw-practice-desk.py"
Icon=cw-practice-desk
StartupWMClass=cw-practice-desk
Categories=Education;HamRadio;
Terminal=false
EOD
update-desktop-database ~/.local/share/applications 2>/dev/null || true
echo "Installed. Look for CW Practice Desk in your app menu."
