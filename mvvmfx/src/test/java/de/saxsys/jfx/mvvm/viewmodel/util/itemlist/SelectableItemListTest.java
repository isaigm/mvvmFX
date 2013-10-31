/*******************************************************************************
 * Copyright 2013 Alexander Casall
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.saxsys.jfx.mvvm.viewmodel.util.itemlist;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ModelToStringMapper;
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.SelectableItemList;

/**
 * Tests for {@link SelectableItemList}.
 * 
 * @author sialcasa
 * 
 */
public class SelectableItemListTest {

	private final Person person3 = new Person("Person 1");
	private final Person person2 = new Person("Person 2");
	private final Person person1 = new Person("Person 3");
	private ModelToStringMapper<Person> stringMapper;
	private ObservableList<Person> listWithModelObjects;
	private SelectableItemList<Person> selectableItemList;
	private IntegerProperty selectedIndex;
	private ObjectProperty<Person> selectedItem;

	/**
	 * Prepares the test.
	 */
	@Before
	public void init() {

		// Create the items in the model
		listWithModelObjects = FXCollections.observableArrayList();
		listWithModelObjects.add(person1);
		listWithModelObjects.add(person2);
		listWithModelObjects.add(person3);

		// Create the converter
		stringMapper = new ModelToStringMapper<Person>() {
			@Override
			public String toString(Person object) {
				return object.name;
			}
		};

		// Convenience
		selectableItemList = new SelectableItemList<Person>(
				listWithModelObjects, stringMapper);
		selectedIndex = selectableItemList.selectedIndexProperty();
		selectedItem = selectableItemList.selectedItemProperty();
	}

	/**
	 * Check whether the internal state is correct.
	 */
	@Test
	public void checkStartState() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(listWithModelObjects.get(0), selectedItem.get());
	}

	/**
	 * Check whether the {@link #selectedItem} changes when the
	 * {@link #selectedIndex} was changed.
	 */
	@Test
	public void setSelectedItemByIndex() {
		selectedIndex.set(1);
		Assert.assertEquals(listWithModelObjects.get(1), selectedItem.get());
	}

	/**
	 * Check whether the {@link #selectedIndex} changes when the
	 * {@link #selectedItem} was changed.
	 */
	@Test
	public void setSelectedIndexByItem() {
		selectedItem.set(person3);
		Assert.assertEquals(2, selectedIndex.get());
	}

	/**
	 * Check whether changes are refused when an invalid {@link #selectedItem}
	 * was set.
	 */
	@Test
	public void setSelectedIndexWithInvalidItem() {
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(person1, selectedItem.get());
		selectedItem.set(null);
		Assert.assertEquals(0, selectedIndex.get());
		Assert.assertEquals(person1, selectedItem.get());
		selectedItem.set(person2);
		Assert.assertEquals(1, selectedIndex.get());
		Assert.assertEquals(person2, selectedItem.get());
	}

}
