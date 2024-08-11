import java.util.*;

public class Solutions {
}

// ===============================================
// =============================================== Count minimal number of jumps from position X to Y. 
// ===============================================
class FrogRiverOne {
        public int solution(int X, int[] A) {
        
        //map: left : time
        //1-X positions
        //for every position find min index
        //get max index of these positions

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < A.length; i++) {
            if (map.containsKey(A[i])) {
                continue;
            }
            map.put(A[i], i);
        }

        int time = -1;
        for (int i = 1; i < X + 1; i++) {
            if (!map.containsKey(i)) {
                return -1;
            }
            time = Math.max(time, map.get(i));
        }

        return time;
    }
}
// ===============================================
// =============================================== Find the missing element in a given permutation. 
// ===============================================
class PermCheck {
    public int solution(int[] A) {
        Set<Integer> set = new HashSet<>();

        for (int n : A) {
            if (set.contains(n)) {
                return 0;
            }
            set.add(n);
        }

        for (int i = 1; i < A.length + 1; i++) {
            if (!set.contains(i)) {
                return 0;
            }
        }
        return 1;
    }
}
// ===============================================
// =============================================== Calculate the values of counters after applying all alternating operations: increase counter by 1; set value of all counters to current maximum. 
// ===============================================
class MaxCounters {
            public int[] solution(int N, int[] A) {
                //track max count so far
                //if > N Arrays.fill(maxCnt)

                int[] res = new int[N];
                int maxCnt = 0;
                int startLine = 0;
                for (int i = 0; i < A.length; i++) {
                    int j = A[i];
                    if (j > N) {
                        startLine = maxCnt;
                        continue;
                    }
                    if (res[j - 1] < startLine) {
                        res[j - 1] = startLine + 1;
                    } else {
                        res[j - 1] += 1;
                    }

                    maxCnt = Math.max(maxCnt, res[j - 1]);
                }

                for (int i = 0; i < res.length; i++) {
                    if (res[i] < startLine) {
                        res[i] = startLine;
                    }
                }

            return res;
    }
}
// ===============================================
// =============================================== Count the number of passing cars on the road. 
// ===============================================
class PassingCars {
        public int solution(int[] A) {
        int eastCarsSeen = 0;
        int pairs = 0;

        for (int c : A) {
            if (c == 0) {
                eastCarsSeen+=1;
            } else {
                pairs += eastCarsSeen;
                if (pairs > 1_000_000_000) {
                    return -1;
                }
            }
        }

        return pairs;
    }
}
// ===============================================
// =============================================== Maximize A[P] * A[Q] * A[R] for any triplet (P, Q, R). 
// ===============================================
class MaxProductOfThree {
    public int solution(int[] A) {
        Arrays.sort(A);

        int max = A[A.length - 1] * A[A.length - 2] * A[A.length - 3];
        if (A[0] < 0 && A[1] < 0) {
            max = Math.max(max, (A[0] * A[1] * A[A.length - 1]));
        }
        return max;
    }
}
// ===============================================
// =============================================== Compute the number of intersections in a sequence of discs.
// ===============================================
class  NumberOfDiscIntersections {
    public int solution(int[] A) {
        if (A.length == 0) {
            return 0;
        }
        int[] start = new int[A.length];
        int[] end = new int[A.length];

        for (int i = 0; i < A.length; i++) {
            start[i] = i - A[i];
            end[i] = i + A[i];
        }
        Arrays.sort(start);
        Arrays.sort(end);

        int intersec = 0;
        int open = 0;
        int s = 0;
        int e = 0;
        while (s < end.length) {
            if (start[s] <= end[e]) {
                intersec += open;
                if (intersec > 10_000_000) {
                    return -1;
                }
                open+=1;
                s+=1;
            } else {
                e+=1;
                open-=1;
            }
        }
        return intersec;
    }
}
// ===============================================
// =============================================== Determine whether a given string of parentheses (multiple types) is properly nested. 
// ===============================================
class Brackets {

    private final Map<Character, Character> map = Map.of(
        '}', '{',
        ')', '(',
        ']', '['
    );

    public int solution(String S) {
        if (S.isEmpty()) {
            return 1;
        }
        if (!map.values().contains(S.charAt(0))) {
            return 0;
        }
        char[] chars = S.toCharArray();
        Stack<Character> open = new Stack<>();

        for (char c : chars) {
            if ('(' == c || '{' == c || '[' == c) {
                open.push(c);
            } else {
                if (open.isEmpty() || open.peek() != map.get(c)) {
                    return 0;
                } else {
                    open.pop();
                }
            }
        }

        return open.isEmpty() ? 1 : 0;
    }
}
// ===============================================
// =============================================== Divide array A into K blocks and minimize the largest sum of any block.
// ===============================================
class MinMaxDivision {
    public int solution(int K, int M, int[] A) {
        int maxSum = 0;
        int minSum = 0;
        for (int n : A) {
            maxSum+=n;
            minSum = Math.max(minSum, n);
        }
        return binarySearch(K, A, minSum, maxSum); //the main idea is to binary search in range: [M, arraySum]
    }

    private int binarySearch(int K, int[] A, int minSum, int maxSum) {
        int res = maxSum;
        
        while (minSum <= maxSum) {
            int mid = (maxSum + minSum) / 2;
            if (isValid(A, K, mid)) {
                res = mid;
                maxSum = mid - 1;
            } else {
                minSum = mid + 1;
            }
        }
        return res;
    }

    private boolean isValid(int[] A, int K, int mid) {
        int curSum = 0;
        int splits = 0;

        for (int n : A) {
            if (curSum + n <= mid) {
                curSum+=n;
            } else {
                curSum = n;
                splits+=1;
            }
        }
        return splits < K;
    }
}
// ===============================================
// ===============================================
// ===============================================
class MaxNonoverlappingSegments {
    public int solution(int[] A, int[] B) {
        int curPos = -1;
        int segments = 0;

        for (int i = 0; i < A.length; i++) {
            if (A[i] > curPos) {
                curPos = B[i];
                segments+=1;
            }
        }
        return segments;
    }
}
}
// ===============================================
// =============================================== returns the minimum number of drivers that must turn back so that the bridge will not be overloaded.
// ===============================================
class CarsBridge {
        public int solution(int[] cars, int k) { //K - weigth limit, car[i] -> ith car weigth
            int l = 0;
            int r = 1;
            int skip = 0;
            while (r < cars.length) {
              if (cars[l] + cars[r] > k) {
                skip+=1;
                if (cars[l] > cars[r]) { //trick: we are deciding to skip heaviest car
                  l = r;
                }
              } else {
                l = r;
              }
              r+=1;
            }
            return skip;
        }
        //just counts skips, not working for optimal skips count, but readable 
        public int solution_GPT(int U, int[] weight) {

            Queue<Integer> bridge = new LinkedList<>();
            int curWeight = 0;
            int skip = 0;
        
            for (int i = 0; i < weight.length; i++) {
              if (bridge.size() < 2) {
        
                if (curWeight + weight[i] <= U) {
                  curWeight += weight[i];
                  bridge.add(weight[i]);
                } else {
                  skip += 1;
                }
              } else {
        
                curWeight -= bridge.poll();
                if (curWeight + weight[i] <= U) {
                  curWeight += weight[i];
                  bridge.add(weight[i]);
                } else {
                  skip += 1;
                }
              }
            }
            return skip;
  }
}
