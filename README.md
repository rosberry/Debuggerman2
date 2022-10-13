# Debuggerman 2

Service-based agent providing access to configurable testing menu for single-activity applications.

## Features

- Quick access to testing menu with notification
- Dynamic elements addition in runtime
- Stacktrace capturing in case of application crash

## Requirements

- Java 8+
- Android API 21+

## Setup

Add a dependency

```groovy
implementation 'com.rosberry.android:debuggerman2:1.0.1'
```

## Usage

### Initialization

Declare a service in application manifest:

```xml
<manifest ... >
    <application ... >

        <service android:name="com.rosberry.android.debuggerman2.service.DebugAgentService"
            android:exported="false" />
    </application>
</manifest>
```

Initialize agent with `DebuggermanDialog` (or derived) parameter in activity `onCreate` method:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    DebuggermanAgent.init<DebuggermanDialog>(this)

    super.onCreate(savedInstanceState)
    
    /* ... */
}
```

### Components customization

Extend `DebuggermanItem` with data class that will hold custom component and corresponding adapter delegate for the dialog:

```kotlin
data class DebuggermanSubtitle(
    val label: String,
    override val group: String? = null
) : DebuggermanItem(DebuggermanSubtitleDelegate::class)

class DebuggermanSubtitleDelegate : DebuggermanAdapterDelegate(R.layout.item_subtitle) {

    override fun createViewHolder(parent: ViewGroup) = object : RecyclerView.ViewHolder(inflate(parent)) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DebuggermanItem) {
        holder.itemView.findViewById<TextView>(R.id.label)?.text = (item as? DebuggermanSubtitle)?.label
    }
}
```

### Dialog customization

Extend `DebuggermanDialog` with required implementation and overridden `items` collection for dialog with static set of components:

```kotlin
class DerivedDebugDialog : DebuggermanDialog() {

    override val items: MutableList<DebuggermanItem> = mutableListOf(
        /* ... */
    )
}
```

or add required components dynamically.

### Dynamic components addition / removal

To add, remove or replace specific item within dialog component list, use corresponding static methods of `DebuggermanAgent` class:

```kotlin
val item: DebuggermanItem = /* ... */

DebuggermanAgent.add(item)

DebuggermanAgent.remove(item)

DebuggermanAgent.replace(target, item)
```

**Note**: Since in most cases code enclosed into component's lambda will be related to some scope, don't forget to remove added items when their scope is destroying to prevent memory leaks.

## About

<img src="https://github.com/rosberry/Foundation/blob/master/Assets/full_logo.png?raw=true" height="100" />

This project is owned and maintained by [Rosberry](http://rosberry.com). We build mobile apps for users worldwide 🌏.

Check out our [open source projects](https://github.com/rosberry), read [our blog](https://medium.com/@Rosberry) or give
us a high-five on 🐦 [@rosberryapps](http://twitter.com/RosberryApps).

## License

Product Name is available under the MIT license. See the LICENSE file for more info.
