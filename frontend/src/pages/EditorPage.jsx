import { useState, useCallback, useMemo } from 'react'
import CodeMirror from '@uiw/react-codemirror'
import { javascript } from '@codemirror/lang-javascript'
import { java } from '@codemirror/lang-java'
import { githubDark } from '@uiw/codemirror-theme-github'

function EditorPage() {
  const [language, setLanguage] = useState('javascript')
  const [value, setValue] = useState("console.log('Hello World!');")

  const onChange = useCallback((val) => {
    setValue(val)
  }, [])

  const extensions = useMemo(() => {
    if (language === 'java') {
      return [java()]
    }
    return [javascript({ jsx: true })]
  }, [language])

  const handleLanguageChange = (event) => {
    const nextLanguage = event.target.value
    setLanguage(nextLanguage)

    if (nextLanguage === 'java') {
      setValue('public class Main {\n    public static void main(String[] args) {\n        System.out.println("Hello World");\n    }\n}')
    } else {
      setValue("console.log('Hello World!');")
    }
  }

  return (
    <div className="editor-shell">
      <div className="editor-toolbar">
        <label htmlFor="language-select">Select Language</label>
        <select id="language-select" value={language} onChange={handleLanguageChange}>
          <option value="javascript">JavaScript</option>
          <option value="java">Java</option>
        </select>
      </div>

      <CodeMirror
        value={value}
        height="450px"
        theme={githubDark}
        extensions={extensions}
        onChange={onChange}
      />
    </div>
  )
}

export default EditorPage
