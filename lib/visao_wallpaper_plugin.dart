
import 'visao_wallpaper_plugin_platform_interface.dart';

class VisaoWallpaperPlugin {
  Future<String?> getPlatformVersion() {
    return VisaoWallpaperPluginPlatform.instance.getPlatformVersion();
  }
}
