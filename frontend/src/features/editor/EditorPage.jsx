import Card from '../../components/ui/Card'

function EditorPage() {
  return (
    <div className="page-stack">
      <div className="page-header">
        <div>
          <p className="eyebrow">Workspace</p>
          <h2>Code editor</h2>
        </div>
      </div>

      <Card title="Starter editor" description="This is where your Monaco or custom editor will be integrated.">
        <div className="editor-surface">
          <textarea defaultValue={'function solve(input) {\n  return input\n}'} />
        </div>
      </Card>
    </div>
  )
}

export default EditorPage
