import { useState } from "react";
import Editor from "@monaco-editor/react";

function EditorPage({handleLanguageChange, code, language, setCode, setLanguage}) {

  return (
    <div className="editor-shell">
      <div className="editor-toolbar">
        <label htmlFor="language-select">Language</label>

        <select
          id="language-select"
          value={language}
          onChange={handleLanguageChange}
        >
          <option value="java">Java</option>
          <option value="python">Python</option>
        </select>
      </div>

      <Editor
        height="500px"
        language={language}
        theme="hc-black"
        value={code}
        onChange={(value) => setCode(value || "")}
        options={{
          fontSize: 15,
          automaticLayout: true,
          minimap: {
            enabled: false,
          },
          scrollBeyondLastLine: false,
          tabSize: 4,
          wordWrap: "on",
          formatOnPaste: true,
          formatOnType: true,
        }}
      />
    </div>
  );
}

export default EditorPage;