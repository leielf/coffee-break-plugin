package org.example.coffeebreakplugin;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.project.Project;

public class TypingTracker {

    public static void attach(Project project) {
        com.intellij.openapi.editor.EditorFactory.getInstance()
                .addEditorFactoryListener(new EditorFactoryListener() {

                    @Override
                    public void editorCreated(EditorFactoryEvent event) {
                        event.getEditor().getDocument().addDocumentListener(new DocumentListener() {

                            @Override
                            public void documentChanged(DocumentEvent event) {
                                ActivityTracker.edits++;
                            }
                        });
                    }
                }, project);
    }
}
