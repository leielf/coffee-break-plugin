package org.example.coffeebreakplugin;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.project.Project;

/**
 * The TypingTracker class monitors typing activity within the editor.
 * It listens for document changes to track user edits in real-time.
 *
 * Each detected edit is forwarded to the ActivityTracker to count the number of edits,
 * enabling the plugin to decide when to notify the user to take a break.
 */
public class TypingTracker {

    /**
     * Attaches the TypingTracker to the specified project. This method sets up:
     * 1. Listeners for all currently open editor instances.
     * 2. A factory listener to monitor future editor creations and attach listeners to them.
     *
     * @param project The IntelliJ project to which the TypingTracker is being attached.
     */
    public static void attach(Project project) {
        var editorFactory = com.intellij.openapi.editor.EditorFactory.getInstance();

        // 1. Attach to already opened editors
        for (var editor : editorFactory.getAllEditors()) {
            attachListener(editor.getDocument());
        }

        // 2. Listen for future editors
        editorFactory.addEditorFactoryListener(new EditorFactoryListener() {
            @Override
            public void editorCreated(EditorFactoryEvent event) {
                attachListener(event.getEditor().getDocument());
            }
        }, project);
    }

    /**
     * Attaches a DocumentListener to the specified document. This listener listens for
     * document change events (e.g., typing, pasting, or deleting text) and updates the
     * ActivityTracker with each detected edit.
     *
     * @param document The target document to attach the listener to.
     */
    private static void attachListener(com.intellij.openapi.editor.Document document) {
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(DocumentEvent event) {
                ActivityTracker.incrementEdits();
            }
        });
    }
}