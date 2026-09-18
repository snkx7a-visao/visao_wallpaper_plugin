import 'package:flutter_test/flutter_test.dart';
import 'package:visao_wallpaper_plugin/visao_wallpaper_plugin.dart';
import 'package:visao_wallpaper_plugin/visao_wallpaper_plugin_platform_interface.dart';
import 'package:visao_wallpaper_plugin/visao_wallpaper_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockVisaoWallpaperPluginPlatform
    with MockPlatformInterfaceMixin
    implements VisaoWallpaperPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final VisaoWallpaperPluginPlatform initialPlatform = VisaoWallpaperPluginPlatform.instance;

  test('$MethodChannelVisaoWallpaperPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelVisaoWallpaperPlugin>());
  });

  test('getPlatformVersion', () async {
    VisaoWallpaperPlugin visaoWallpaperPlugin = VisaoWallpaperPlugin();
    MockVisaoWallpaperPluginPlatform fakePlatform = MockVisaoWallpaperPluginPlatform();
    VisaoWallpaperPluginPlatform.instance = fakePlatform;

    expect(await visaoWallpaperPlugin.getPlatformVersion(), '42');
  });
}
