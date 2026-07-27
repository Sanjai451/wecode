import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <h1> Hello world </h1>

      <h4>Bro when you create any files inside any folder remove the empty file. If that file hasn't added then git wont consider to push that particual folder to github.</h4>
    </>
  )
}

export default App
