import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'visao_wallpaper_plugin_platform_interface.dart';

/// An implementation of [VisaoWallpaperPluginPlatform] that uses method channels.
class MethodChannelVisaoWallpaperPlugin extends VisaoWallpaperPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('visao_wallpaper_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
