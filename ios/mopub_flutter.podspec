#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'mopub_flutter'
  s.version          = '0.0.1'
  s.summary          = 'Mopub plugin that shows banner ads using native platform views.'
  s.description      = <<-DESC
  A new flutter plugin project.
  DESC
  s.homepage         = 'https://github.com/bohdanuhryn/mopub_flutter'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Bohdan Uhrynovskyi' => 'bohdanuhryn@gmail.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency 'mopub-ios-sdk', '5.7.0'
  s.dependency 'MoPub-AdMob-Adapters', '7.46.0.0'
  
  s.static_framework = true
  
  s.ios.deployment_target = '9.0'
  
end
