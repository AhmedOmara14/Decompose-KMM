import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    var root :RootHolder{
        appDelegate.rootHolder
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(rootComponent: root.rootComponent)
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)){_ in
                     debugPrint("Swift onResume")
                     LifecycleRegistryExtKt.resume(root.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)){_ in
                     debugPrint("Swift onPause")
                     LifecycleRegistryExtKt.pause(root.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)){_ in
                     debugPrint("Swift onStop")
                     LifecycleRegistryExtKt.stop(root.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)){_ in
                     debugPrint("Swift onDestroy")
                     LifecycleRegistryExtKt.destroy(root.lifecycle)
                     root.lifecycle.unsubscribe(callbacks: LifecycleCallbackImpl())
                }
        }
    }
}

class RootHolder :ObservableObject {
    
    let lifecycle : LifecycleRegistry
    let rootComponent : RootComponent
    
    init (){
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        lifecycle.subscribe(callbacks: LifecycleCallbackImpl())
        
        let homeViewModel = HomeViewModel()
        
        rootComponent = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            homeViewModel: homeViewModel
            )
        
        LifecycleRegistryExtKt.create(lifecycle)
    }
    
    deinit{
        LifecycleRegistryExtKt.destroy(lifecycle)
        
    }
}

class AppDelegate :NSObject,UIApplicationDelegate {
    let rootHolder = RootHolder()
}
