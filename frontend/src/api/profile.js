const demoProfileData = {
  metrics: [
    { label: 'Solved', value: '23' },
    { label: 'Attempts', value: '48' },
    { label: 'Streak', value: '6 days' },
  ],
  submissions: [
    { id: 1, title: 'Two Sum', status: 'Accepted', language: 'Java', date: 'Just Now' },
    { id: 2, title: 'Valid Parentheses', status: 'Wrong Answer', language: 'JavaScript', date: 'Yesterday' },
    { id: 3, title: 'Merge Intervals', status: 'Accepted', language: 'Java', date: '2 days ago' },
  ],
  activity: [
    { day: 'Mon', value: 2 },
    { day: 'Tue', value: 4 },
    { day: 'Wed', value: 3 },
    { day: 'Thu', value: 5 },
    { day: 'Fri', value: 2 },
    { day: 'Sat', value: 4 },
    { day: 'Sun', value: 1 },
  ],
}

export async function getProfileData(userId) {
  return Promise.resolve({
    userId,
    ...demoProfileData,
  })
}
