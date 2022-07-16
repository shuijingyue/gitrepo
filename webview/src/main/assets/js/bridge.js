class NativeBridgeDelegate {
  constructor() {
    const platform = {};
    platform.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
    platform.isAndroid = !platform.isIOS;
    this.platform = platform;

    this.nativeBridge = platform.isAndroid
      ? window.nativeBridge
      : window.webkit.messageHandlers.nativeBridge;

    this.callbacks = {};
  }

  postMessage(method, params, callback) {
    const message = { method, params };
    if (callback instanceof Function) {
      const callbackname =
        "nativetojs_callback_" +
        new Date().getTime() +
        "_" + Math.floor(Math.random() * 10000);
			this.callbacks[callbackname] = callback;	
			message.callback = callbackname;
    }
		this.nativeBridge.postMessage(JSON.stringify(message));
  }

	saveMessage(method, params) {
		const callback = this.callbacks[method];

		if (callback == undefined) return;

		callback(JSON.parse(params));

		delete this.callbacks[method];
	}
}

const nativeBridgeDelegate = new NativeBridgeDelegate()
window.nativeBridgeDelegate = nativeBridgeDelegate
