
# Capacitor NavigationBar
This is a capacitor plugin to control the navigation bar on Android devices.
## Install

```bash
npm install @squareetlabs/capacitor-navigation-bar
ionic cap sync
```


This plugin is for Capacitor and allows control of the navigation bar on Android devices.

## Summary

The Capacitor NavigationBar plugin is designed to provide an easy way to control the navigation bar on Android devices, allowing you to hide, show, change its color, and make it transparent.

## API Docs
For a more in-depth look into the different parameters for methods, along with the corresponding types. Please look into a breakdown of the API:

[API Documentation](api-docs.md)

### Methods

- **hide()**  
  Hides the navigation bar.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.hide();
  ```

- **show()**  
  Shows the navigation bar.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.show();
  ```

- **setTransparency(options: { isTransparent: boolean })**  
  Sets the transparency of the navigation bar.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.setTransparency({ isTransparent: true });
  ```

- **setColor(options: { color: string, darkButtons: boolean })**  
  Sets the color of the navigation bar and the style of the buttons (light or dark).

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.setColor({ color: '#FF0000', darkButtons: true });
  ```

- **getColor()**  
  Gets the current color of the navigation bar.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  const color = await NavigationBar.getColor();
  console.log(color); // { color: '#FF0000' }
  ```

### Listeners

- **addListener(event: 'onShow', listenerFunc: () => void)**  
  Event fired after the navigation bar is displayed.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.addListener('onShow', () => {
  console.log('Navigation bar is shown');
  });
  ```

- **addListener(event: 'onHide', listenerFunc: () => void)**  
  Event fired after the navigation bar is hidden.

  ```typescript
  import { Plugins } from '@capacitor/core';
  const { NavigationBar } = Plugins;

  NavigationBar.addListener('onHide', () => {
  console.log('Navigation bar is hidden');
  });
  ```

- **addListener(event: 'onColorChange', listenerFunc: (returnObject: { color: string }) => void)**  
Event fired after the navigation bar color is changed.

```typescript
import { Plugins } from '@capacitor/core';
const { NavigationBar } = Plugins;

NavigationBar.addListener('onColorChange', (info) => {
console.log('Navigation bar color changed to', info.color);
});
```

## More In-Depth Review of the Plugin

The Capacitor NavigationBar plugin provides an efficient and easy way to control the navigation bar on Android devices. With methods to hide, show, change the color, and adjust the transparency, it offers great flexibility and control over your app's user interface.

## Limitations

- This plugin is designed specifically for Android devices and has no effect on iOS.
- Full transparency of the navigation bar is only supported on Android R (11) and above. On earlier versions, you can use the FLAG_LAYOUT_NO_LIMITS flag to achieve a similar effect.
