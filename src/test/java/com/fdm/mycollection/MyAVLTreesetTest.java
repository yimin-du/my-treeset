package com.fdm.mycollection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.*;

public class MyAVLTreesetTest {

	private MyAVLTreeSet<Integer> tree;

	@Before
	public void setup() {
		tree = new MyAVLTreeSet<Integer>();
	}
	
	@Test
	public void set_return_correct_size_after_insertion() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		assertEquals(3, tree.size());
	}
	
	@Test 
	public void insertion_with_right_rotate_generate_correct_tree_structure() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		tree.insert(8);
		tree.insert(7);
		
		List<Integer> orderedNodes = tree.getNodes();
		List<Integer> orderedNodesExpected = Arrays.asList(0,5,7,8,9,10);
		assertEquals(orderedNodes, orderedNodesExpected);
	}
	
	@Test 
	public void insertion_with_left_rotate_generate_correct_tree_structure() {
		tree.insert(5);
		tree.insert(0);
		tree.insert(7);
		tree.insert(9);
		tree.insert(11);
		
		List<Integer> orderedNodes = tree.getNodes();
		List<Integer> orderedNodesExpected = Arrays.asList(0,5,7, 9,11);
		assertEquals(orderedNodes, orderedNodesExpected);
	}
	
	@Test 
	public void insertion_with_various_rotate_generate_correct_tree_structure() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		tree.insert(6);
		tree.insert(11);
		tree.insert(-1);
		tree.insert(1);
		tree.insert(2);
		
		List<Integer> orderedNodes = tree.getNodes();
		List<Integer> orderedNodesExpected = Arrays.asList(-1,0,1,2,5,6,9,10,11);
		assertEquals(orderedNodes, orderedNodesExpected);
	}
	
	@Test
	public void set_return_correct_size_after_delete() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.delete(5);
		tree.delete(9);
		assertEquals(1, tree.size());
	}
	
	@Test
	public void return_correct_tree_after_delete() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		tree.insert(6);
		tree.insert(11);
		tree.insert(-1);
		tree.insert(1);
		tree.insert(2);
		tree.delete(6);
		tree.delete(1);
		tree.delete(9);
		List<Integer> orderedNodes = tree.getNodes();
		List<Integer> orderedNodesExpected = Arrays.asList(-1,0,2,5,10,11);
		assertEquals(orderedNodes, orderedNodesExpected);
	}
	
	@Test
	public void iterator_return_ordered_nodes() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		tree.insert(6);
		tree.insert(11);
		
		List<Integer> orderedNodes = new ArrayList<>();
		Iterator<Integer> it = tree.iterator();
		while(it.hasNext()) {
			orderedNodes.add(it.next());
		}
		
		List<Integer> orderedNodesExpected = Arrays.asList(0,5,6,9,10,11);
		assertEquals(orderedNodes, orderedNodesExpected);
	}
	
	
	@Test
	public void return_true_if_key_exists() {
		tree.insert(9);
		tree.insert(5);
		tree.insert(10);
		tree.insert(0);
		assertEquals(true, tree.contains(5));
	}
}
