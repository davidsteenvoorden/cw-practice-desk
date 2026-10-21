#!/usr/bin/env python3
"""CW Practice Desk: a small desktop window around web/index.html (GTK3 + WebKit2GTK)."""
import os

# NVIDIA + Wayland: WebKit's DMA-BUF renderer kills the connection ("Error 71 (Protocol error)").
os.environ.setdefault("WEBKIT_DISABLE_DMABUF_RENDERER", "1")

import gi  # noqa: E402

gi.require_version("Gtk", "3.0")
gi.require_version("WebKit2", "4.1")
from gi.repository import GLib, Gtk, WebKit2  # noqa: E402

HERE = os.path.dirname(os.path.realpath(__file__))
PAGE = os.path.join(HERE, "..", "web", "index.html")
DATA = os.path.join(os.environ.get("XDG_DATA_HOME", os.path.expanduser("~/.local/share")), "cw-practice-desk")
CACHE = os.path.join(os.environ.get("XDG_CACHE_HOME", os.path.expanduser("~/.cache")), "cw-practice-desk")


def main():
    GLib.set_prgname("cw-practice-desk")  # Wayland app id, matches the .desktop file
    GLib.set_application_name("CW Practice Desk")
    # Persistent storage so lesson progress and settings (localStorage) survive restarts.
    manager = WebKit2.WebsiteDataManager(base_data_directory=DATA, base_cache_directory=CACHE)
    context = WebKit2.WebContext.new_with_website_data_manager(manager)
    view = WebKit2.WebView.new_with_context(context)

    settings = view.get_settings()
    settings.set_enable_webaudio(True)
    settings.set_media_playback_requires_user_gesture(False)
    settings.set_enable_developer_extras(False)
    view.connect("context-menu", lambda *a: True)  # no "Reload/Inspect" menu

    win = Gtk.Window(title="CW Practice Desk")
    win.set_default_size(1180, 820)
    win.set_icon_name("cw-practice-desk")
    win.add(view)
    win.connect("destroy", Gtk.main_quit)

    view.load_uri("file://" + os.path.abspath(PAGE))
    win.show_all()
    Gtk.main()


if __name__ == "__main__":
    main()
