import { useAuth } from '../../hooks/useAuth'
import Card from '../../components/ui/Card'

function ProfilePage() {
  const { user } = useAuth()

  return (
    <div className="page-stack">
      <div className="page-header">
        <div>
          <p className="eyebrow">Account</p>
          <h2>Profile</h2>
        </div>
      </div>

      <Card title="Account details" description="Your personal workspace details will appear here.">
        <div className="profile-details">
          <p><strong>Name:</strong> {user?.name || 'Guest'}</p>
          <p><strong>Email:</strong> {user?.email || 'Not signed in'}</p>
          <p><strong>Role:</strong> {user?.role || 'student'}</p>
        </div>
      </Card>
    </div>
  )
}

export default ProfilePage
