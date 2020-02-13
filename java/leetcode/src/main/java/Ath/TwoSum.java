package Ath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
    /*public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int ans=0;
        int templen=0;
        for(int i=0,j=0;j<s.length();j++){
            if(map.containsKey(s.charAt(j))){
                i=map.get(s.charAt(j))+1;
                ans = Math.max(ans,templen);
                templen = j-i+1;
            } else {
                templen++;
            }
            map.put(s.charAt(j), j);
        }
        ans = Math.max(ans,templen);
        return ans;
    }*/


    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i1=0;
        int i2=0;
        int m=nums1.length;int n= nums2.length;
        int count = (m+n-2)/2;
        for(int index=0;index<count;index++){
            if(i1>=m){
                i2++;
            } else if(i2>=n){
                i1++;
            } else if(nums1[i1]<nums2[i2]){
                i1++;
            } else
                i2++;
        }
        int part1 = nums1[i1]<nums2[i2]? nums2[i2]:nums1[i1];
        if(i1>=m){
            i2++;
        } else if(i2>=n){
            i1++;
        } else if(nums1[i1]<nums2[i2]){
            i1++;
        } else
            i2++;

        int part2 = nums1[i1]>nums2[i2]? nums2[i2]:nums1[i1];
        if((n+m)%2==1){
            return part2;
        } else{
            return (part1+part2)/2.0;
        }
    }

    public static void main(String[] args){
        TwoSum a = new TwoSum();
        int[] nums1 = {1,2};
        int[] nums2 = {3,4};
        System.out.println(a.findMedianSortedArrays(nums1, nums2));
    }
}
