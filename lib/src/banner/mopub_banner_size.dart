class MopubBannerSize {

    final int width;
    final int height;
    final String name;

    static const MopubBannerSize BANNER = MopubBannerSize(width: 320, height: 50, name: 'BANNER');
    static const MopubBannerSize MEDIUM_RECTANGLE = MopubBannerSize(width: 300, height: 250, name: 'MEDIUM_RECTANGLE');

    const MopubBannerSize({
        this.width,
        this.height,
        this.name,
    });

    Map<String, dynamic> get toMap => <String, dynamic>{
        'width': width,
        'height': height,
        'name': name
    };

}