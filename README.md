
# Coffee Break Plugin

☕ **Coffee Break Plugin** is an IntelliJ IDEA plugin designed to promote healthy coding habits by monitoring your activity and reminding you to take breaks. This helps improve focus, avoid burnout, and boost long-term productivity.

---

## Key Features

- **Typing Activity Tracker**: Tracks the number of edits you make in real time to monitor activity.
- **Timed Break Reminders**: Alerts you to take a coffee break when specific thresholds (time or edits) are met.
- **AI-Generated Break Messages**: Uses APIs (Ollama & Hugging Face) to send personalized, fun break suggestions.
- **Seamless Integration**: Works automatically with all files in your project.

---

## How It Works

1. **Activity Tracking**:
   - The plugin uses the `TypingTracker` to listen for document changes (e.g., typing, pasting) in all files within the project.
   - Tracks the number of edits and their duration.

2. **Break Notification**:
   - Runs a timer (`CoffeeBreakTimer`) to periodically check your activity.
   - If the time (`50+ minutes`) or edit count (`30,000+ edits`) thresholds are met:
     - Sends a fun break notification.
     - Resets tracking for the next session.

3. **Custom AI Messages**:
   - Uses either the **first available model from Ollama** or the Hugging Face API to generate fun, creative break messages. 
   - If neither service is available, it falls back to a default static message.
     
---

## Configuration

You can configure the plugin from the **Settings** menu:

1. Navigate to `File > Settings > Tools > Coffee Break Plugin`.
2. Paste Hugging Face API key.
3. Save your changes.

---

## Example Use Case

- Edit code in your project while the plugin tracks your activity.
- After reaching 50 minutes of typing, a notification will encourage you to:
  - "☕ Take a short coffee break, do 10 squats or perform a moonwalk!"

---

## Supported APIs

This plugin integrates with AI services for generating custom recommendations:
- **Ollama**: A local AI model. When multiple Ollama models are available, the plugin uses the first model provided in the configuration.
- **Hugging Face**: A cloud-based API for generating personalized messages.

> If both are unavailable, it defaults to a static friendly message.

---

## Authors

- **Leila Bbaayeva** 

---

Happy coding and remember, don’t forget your coffee breaks! ☕
