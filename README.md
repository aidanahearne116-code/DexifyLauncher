# DexifyLauncher APK kit

Wraps the launcher page (`www/index.html`) in a Capacitor Android app and builds an installable APK with GitHub Actions. No local Android tooling needed.

## Build it
1. Create a new GitHub repo and upload everything in this folder (keep the `.github` folder).
2. Push to `main`. The **Build APK** workflow runs automatically (or run it from the **Actions** tab with *Run workflow*).
3. When it finishes (about 3-5 minutes), open the run and download **DexifyLauncher-apk** from *Artifacts*. Unzip it to get `app-debug.apk`.
4. Copy the APK to your phone and install it (allow "install unknown apps" when asked).

## Customise
- Edit `www/index.html` to change the launcher, then push again to rebuild.
- App name and ID: `capacitor.config.json`.
- Icon: replace the PNGs in `assets/` (1024x1024). The workflow generates all Android sizes from them.
- Debug APKs are fine for personal use. For Play Store release you would need a signed release build.

## Notes
- Installed-app tiles show the app's real icon straight from your phone (works for sideloaded APKs too) via `native/AppIconPlugin.java`. The Play Store icon is only a fallback.
- Tiles open through the Android launcher plugin: websites open in your default browser, "Installed app" tiles launch by package name and fall back to the Play Store.
- Needs internet for the Google Fonts; falls back to system fonts offline.
