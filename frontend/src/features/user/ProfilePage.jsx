import { useEffect, useState } from 'react'
import { useAuth } from '../../hooks/useAuth'
import { getProfileData } from '../../api/profile'
import Card from '../../components/ui/Card'

function ProfilePage() {
  const { user } = useAuth()
  const [profileData, setProfileData] = useState({ metrics: [], submissions: [], activity: [] })

  useEffect(() => {
    let isMounted = true

    getProfileData(user?.id).then((data) => {
      if (isMounted) {
        setProfileData(data)
      }
    })

    return () => {
      isMounted = false
    }
  }, [user?.id])

  const initials = (user?.name || 'Guest')
    .split(' ')
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() || '')
    .join('') || 'G'

  const profileStats = [
    { label: 'Account ID', value: user?.id ? `#${user.id}` : 'Unavailable' },
    { label: 'Email', value: user?.email || 'Not available' },
    { label: 'Role', value: user?.role ? user.role.toUpperCase() : 'STUDENT' },
  ]

  const completeness = Math.round(
    [user?.name, user?.email, user?.role, user?.id].filter(Boolean).length / 4 * 100,
  )

  const activityValues = profileData.activity.map((item) => item.value)
  const chartMax = Math.max(...activityValues, 5)

  return (
    <div className="profile-page">
      <section className="profile-hero card">
        <div className="profile-avatar">{initials}</div>
        <div className="profile-hero-content">
          <div>
            <p className="eyebrow">Coding profile</p>
            <h2>{user?.name || 'Guest User'}</h2>
            <p className="profile-subtitle">
              {user?.email || 'Sign in to sync your account details.'}
            </p>
          </div>

          <div className="profile-badges">
            <span className="profile-badge">{user?.id ? `User #${user.id}` : 'Guest account'}</span>
            <span className="profile-badge profile-badge-primary">
              {(user?.role || 'student').toUpperCase()}
            </span>
          </div>
        </div>
      </section>

      <section className="profile-grid">
        <Card title="Profile overview" description="A concise snapshot of the account data currently available.">
          <div className="profile-progress">
            <div className="profile-progress-bar" aria-hidden="true">
              <div style={{ width: `${completeness}%` }} />
            </div>
            <p className="profile-progress-label">{completeness}% profile completeness</p>
          </div>

          <div className="profile-stats">
            {profileStats.map((stat) => (
              <div key={stat.label} className="profile-stat-card">
                <span>{stat.label}</span>
                <strong>{stat.value}</strong>
              </div>
            ))}
          </div>
        </Card>

        <Card title="Account details" description="The user information currently exposed by the frontend.">
          <div className="profile-details-list">
            <div>
              <span>Full name</span>
              <strong>{user?.name || 'Not provided'}</strong>
            </div>
            <div>
              <span>Email address</span>
              <strong>{user?.email || 'Not provided'}</strong>
            </div>
            <div>
              <span>Role</span>
              <strong>{user?.role || 'student'}</strong>
            </div>
            <div>
              <span>Account status</span>
              <strong>{user ? 'Active session' : 'Signed out'}</strong>
            </div>
          </div>
        </Card>
      </section>

      <section className="profile-grid profile-grid-bottom">
        <Card title="Recent submissions" description="Latest activity from the current user profile.">
          <div className="submission-list">
            {profileData.submissions.map((submission) => (
              <div key={submission.id} className="submission-item">
                <div>
                  <strong>{submission.title}</strong>
                  <p>{submission.language} • {submission.date}</p>
                </div>
                <span className={`submission-status-pill ${submission.status.toLowerCase().replace(/\s+/g, '-')}`}>
                  {submission.status}
                </span>
              </div>
            ))}
          </div>
        </Card>

        <Card title="Weekly activity" description="A simple visualization of recent daily submissions.">
          <div className="activity-chart" aria-label="Weekly submission activity chart">
            {profileData.activity.map((item) => (
              <div key={item.day} className="activity-column">
                <div className="activity-tooltip">{item.value} submissions</div>
                <div
                  className="activity-bar"
                  style={{ height: `${Math.max(10, (item.value / chartMax) * 100)}%` }}
                  title={`${item.day}: ${item.value} submissions`}
                />
                <span>{item.day}</span>
              </div>
            ))}
          </div>
        </Card>
      </section>
    </div>
  )
}

export default ProfilePage
