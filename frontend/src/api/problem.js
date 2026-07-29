const problems = [
  {
    "id": 1,
    "title": "Two Sum",
    "slug": "two-sum",
    "description": "Given an integer array nums and an integer target, return the indices of the two numbers such that they add up to the target.\n\nExample 1\nInput:\nnums = [2,7,11,15]\ntarget = 9\n\nOutput:\n[0,1]\n\nExplanation:\nnums[0] + nums[1] = 9.\n\nExample 2\nInput:\nnums = [3,2,4]\ntarget = 6\n\nOutput:\n[1,2]",
    "inputFormat": "The first line contains an integer n.\nThe second line contains n space-separated integers.\nThe third line contains the target value.",
    "outputFormat": "Print the indices of the two numbers separated by a space.",
    "constraints": "2 <= n <= 100000\n-1000000000 <= nums[i] <= 1000000000\nExactly one valid answer exists.",
    "explanation": "Store previously visited numbers in a HashMap. For each element, check if target - currentElement already exists in the map.",
    "difficulty": "EASY",
    "timeLimit": 2,
    "memoryLimit": 256,
    "active": true,
    "createdAt": "2026-07-29T09:00:00",
    "updatedAt": "2026-07-29T09:00:00",
    tags: ['Sorting', 'Intervals']
  },
  {
    "id": 2,
    "title": "Longest Substring Without Repeating Characters",
    "slug": "longest-substring-without-repeating-characters",
    "description": "Given a string s, find the length of the longest substring without repeating characters.\n\nExample 1\nInput:\ns = \"abcabcbb\"\n\nOutput:\n3\n\nExplanation:\nThe answer is \"abc\".\n\nExample 2\nInput:\ns = \"bbbbb\"\n\nOutput:\n1\n\nExample 3\nInput:\ns = \"pwwkew\"\n\nOutput:\n3",
    "inputFormat": "A single string s containing lowercase English letters.",
    "outputFormat": "Print the length of the longest substring without repeating characters.",
    "constraints": "0 <= s.length <= 50000\ns consists of printable ASCII characters.",
    "explanation": "Maintain a sliding window using two pointers. Expand the window while characters are unique and shrink it when a duplicate is encountered.",
    "difficulty": "MEDIUM",
    "timeLimit": 2,
    "memoryLimit": 256,
    "active": true,
    "createdAt": "2026-07-29T09:05:00",
    "updatedAt": "2026-07-29T09:05:00",
    tags: ['Sorting', 'Intervals']
  },
  {
    "id": 3,
    "title": "Merge K Sorted Lists",
    "slug": "merge-k-sorted-lists",
    "description": "You are given an array of k linked lists, each sorted in ascending order. Merge all the linked lists into one sorted linked list.\n\nExample 1\nInput:\nlists = [[1,4,5],[1,3,4],[2,6]]\n\nOutput:\n[1,1,2,3,4,4,5,6]\n\nExplanation:\nMerge all three lists into one sorted list.\n\nExample 2\nInput:\nlists = []\n\nOutput:\n[]",
    "inputFormat": "The first line contains an integer k.\nEach of the next k lines contains a sorted linked list.",
    "outputFormat": "Print the merged sorted linked list.",
    "constraints": "0 <= k <= 10000\n0 <= total number of nodes <= 100000\n-10000 <= node.val <= 10000",
    "explanation": "Use a Priority Queue (Min Heap). Insert the head of every list into the heap. Repeatedly extract the minimum node and insert its next node until the heap becomes empty.",
    "difficulty": "HARD",
    "timeLimit": 3,
    "memoryLimit": 512,
    "active": true,
    "createdAt": "2026-07-29T09:10:00",
    "updatedAt": "2026-07-29T09:10:00",
    tags: ['Sorting', 'Intervals']
  }
]


export async function getProblems() {
  return Promise.resolve(problems)
}

export async function getProblemById(id) {
  const problem = problems.find((item) => item.id === Number(id))
  return Promise.resolve(problem || null)
}
