import ComposeApp

func setupKotlinInterop() {
    NativeInterface.shared.delegate = { name in
        SwiftInterface.greet(withName: name)
    }
}