function Card({ title, description, children, action }) {
  return (
    <section className="card">
      <div className="card-header">
        {title ? <h3>{title}</h3> : null}
        {action ? <div>{action}</div> : null}
      </div>
      {description ? <p className="card-description">{description}</p> : null}
      {children}
    </section>
  )
}

export default Card
