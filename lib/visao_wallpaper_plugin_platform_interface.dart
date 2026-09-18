import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'visao_wallpaper_plugin_method_channel.dart';

abstract class VisaoWallpaperPluginPlatform extends PlatformInterface {
  /// Constructs a VisaoWallpaperPluginPlatform.
  VisaoWallpaperPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static VisaoWallpaperPluginPlatform _instance = MethodChannelVisaoWallpaperPlugin();

  /// The default instance of [VisaoWallpaperPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelVisaoWallpaperPlugin].
  static VisaoWallpaperPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [VisaoWallpaperPluginPlatform] when
  /// they register themselves.
  static set instance(VisaoWallpaperPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
