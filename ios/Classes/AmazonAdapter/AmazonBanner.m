#import <Foundation/Foundation.h>
#import "AmazonBanner.h"
#import <AmazonAd/AmazonAdRegistration.h>
#import <AmazonAd/AmazonAdView.h>
#import <AmazonAd/AmazonAdError.h>

@interface AmazonBanner () <AmazonAdViewDelegate>

@property(nonatomic, strong) AmazonAdView *amazonAdView;

@end

@implementation AmazonBanner

- (id)init {
    self = [super init];
    return self;
}

- (void)dealloc {
    self.amazonAdView.delegate = nil;
}

- (void)requestAdWithSize:(CGSize)size customEventInfo:(NSDictionary *)info {
    [[AmazonAdRegistration sharedRegistration] setAppKey:[self getAppKey:info]];
    [[AmazonAdRegistration sharedRegistration] setLogging:true];
    self.amazonAdView = [AmazonAdView amazonAdViewWithAdSize: [self getSize]];
    self.amazonAdView.delegate = self;
    AmazonAdOptions *options = [AmazonAdOptions options];
    options.isTestRequest = YES;
    [self.amazonAdView loadAd:options];
    NSLog(@"requestAdWithSize call");
}

- (NSString*)getAppKey:(NSDictionary*) info {
    NSString *appKey;
    NSString *path = [[NSBundle mainBundle] pathForResource:@"Info" ofType:@"plist"];
    NSDictionary *dict = [[NSDictionary alloc] initWithContentsOfFile:path];
    appKey = [dict valueForKey:@"com.mopub_flutter.amazon_app_key"];
    if (appKey == nil) {
        appKey = [[info objectForKey:@"app_id"] stringValue];
    }
    return appKey;
}

- (CGSize)getSize {
    CGFloat width = [[self.localExtras objectForKey:@"adWidth"] floatValue];
    CGFloat height = [[self.localExtras objectForKey:@"adHeight"] floatValue];
    if (AmazonAdSize_300x250.height == height && AmazonAdSize_300x250.width == width) {
        return AmazonAdSize_300x250;
    } else {
        return AmazonAdSize_320x50;
    }
}

- (CGRect)frameFromSize:(CGSize *)size {
    return CGRectMake(0, 0, size->width, size->height);
}
        
- (UIViewController *)viewControllerForPresentingModalView {
    UIViewController *viewController = [self.localExtras objectForKey:@"viewController"];
    return viewController;
}

- (void)adViewDidLoad:(AmazonAdView *)view {
    [self.delegate inlineAdAdapter:self didLoadAdWithAdView:self.amazonAdView];
    NSLog(@"Amazon ad loaded!!!!!!");
}

- (void)adViewDidFailToLoad:(AmazonAdView *)view withError:(AmazonAdError *)error {
    NSMutableDictionary* userInfo = [NSMutableDictionary dictionary];
    [userInfo setValue:[error errorDescription] forKey:NSLocalizedDescriptionKey];
    NSError *err = [NSError errorWithDomain:@"AmazonAd" code:[error errorCode] userInfo:userInfo];
    [self.delegate inlineAdAdapter:self didFailToLoadAdWithError:err];
    NSLog(@"Ad Failed to load. Error code %d: %@", error.errorCode, error.errorDescription);
}
        
@end
