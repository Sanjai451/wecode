const problems = [
  {
    id: 1,
    title: 'Two Sum',
    difficulty: 'Easy',
    category: 'Arrays',
    description: 'Find two numbers in an array that add up to a target value.',
    tags: ['Hash Map', 'Array'],
  },
  {
    id: 2,
    title: 'Valid Parentheses',
    difficulty: 'Medium',
    category: 'Stack',
    description: 'Validate whether a string of parentheses is balanced.',
    tags: ['Stack', 'String'],
  },
  {
    id: 3,
    title: 'Merge Intervals',
    difficulty: 'Medium',
    category: 'Sorting',
    description: 'Merge overlapping intervals in a list of intervals.',
    tags: ['Sorting', 'Intervals'],
  },
]

export async function getProblems() {
  return Promise.resolve(problems)
}

export async function getProblemById(id) {
  const problem = problems.find((item) => item.id === Number(id))
  return Promise.resolve(problem || null)
}
