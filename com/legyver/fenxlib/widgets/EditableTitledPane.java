package com.legyver.fenxlib.widget;

import com.sun.javafx.css.converters.BooleanConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableProperty;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class EditableTitledPane extends TitledPane {

	private static final String DEFAULT_STYLE_CLASS = "editable-titled-pane";
	private static final PseudoClass PSEUDO_CLASS_EDIT = PseudoClass.getPseudoClass("edit");
	private static final PseudoClass PSEUDO_CLASS_VIEW = PseudoClass.getPseudoClass("view");

	private final BooleanProperty editable = new StyleableBooleanProperty(true) {

		@Override
		public Object getBean() {
			return EditableTitledPane.this;
		}

		@Override
		public String getName() {
			return "editable";
		}

		@Override
		public CssMetaData<EditableTitledPane, Boolean> getCssMetaData() {
			return StyleableProperties.EDITABLE;
		}

	};

	// ---
	private BooleanProperty viewing = new BooleanPropertyBase(true) {
		@Override
		protected void invalidated() {
			final boolean active = get();
			pseudoClassStateChanged(PSEUDO_CLASS_VIEW, active);
			pseudoClassStateChanged(PSEUDO_CLASS_EDIT, !active);
			notifyAccessibleAttributeChanged(AccessibleAttribute.EXPANDED);
		}

		@Override
		public Object getBean() {
			return EditableTitledPane.this;
		}

		@Override
		public String getName() {
			return "view";
		}
	};

	public EditableTitledPane(String text, Node content, boolean editable) {
		super(text, content);
		setEditable(editable);

		getStyleClass().setAll(DEFAULT_STYLE_CLASS);
		// initialize pseudo-class state
		pseudoClassStateChanged(PSEUDO_CLASS_VIEW, true);
	}

	/**
	 * Specifies if the TitledPane can be edited. The default is		 *
	 * {@code true}.
	 */
	public final void setEditable(boolean value) {
		editableProperty().set(value);
	}

	/**
	 * Returns the collapsible state of the TitlePane.
	 *
	 * @return The collapsible state of the TitledPane.
	 */
	public final boolean isEditable() {
		return editable.get();
	}

	/**
	 * The collapsible state of the TitledPane.
	 */
	public final BooleanProperty editableProperty() {
		return editable;
	}

	@Override
	public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
		switch (attribute) {
			case TEXT: {
				String accText = getAccessibleText();
				if (accText != null && !accText.isEmpty()) {
					return accText;
				}
				return getText();
			}
			case EDITABLE:
				return isEditable();
			default:
				return super.queryAccessibleAttribute(attribute, parameters);
		}
	}

	private static class StyleableProperties {

		private static final CssMetaData<EditableTitledPane, Boolean> EDITABLE
				= new CssMetaData<EditableTitledPane, Boolean>("-fx-editable",
						BooleanConverter.getInstance(), Boolean.TRUE) {

			@Override
			public boolean isSettable(EditableTitledPane n) {
				return n.editable == null || !n.editable.isBound();
			}

			@Override
			public StyleableProperty<Boolean> getStyleableProperty(EditableTitledPane n) {
				return (StyleableProperty<Boolean>) (WritableValue<Boolean>) n.editableProperty();
			}
		};

		private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

		static {
			final List<CssMetaData<? extends Styleable, ?>> styleables
					= new ArrayList<CssMetaData<? extends Styleable, ?>>(TextField.getClassCssMetaData());
			styleables.add(EDITABLE);
			STYLEABLES = Collections.unmodifiableList(styleables);
		}
	}

	/**
	 * @return The CssMetaData associated with this class, which may include the
	 * CssMetaData of its super classes.
	 * @since JavaFX 8.0
	 */
	public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
		return StyleableProperties.STYLEABLES;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since JavaFX 8.0
	 */
	@Override
	public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
		return getClassCssMetaData();
	}

}
