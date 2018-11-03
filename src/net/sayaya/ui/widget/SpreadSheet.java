package net.sayaya.ui.widget;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import net.sayaya.ui.style.StyleChart;
import net.sayaya.ui.style.StyleSheet;

public final class SpreadSheet extends ResizeComposite {
	private final LayoutPanel contents = new LayoutPanel();
	private SpreadSheetTable table = null;
	private final SheetSetting setting;
	public SpreadSheet(SheetSetting setting) {
		this.setting = setting;
		initWidget(contents);
		setStyleName(StyleSheet.GSS.sheet());
		contents.addAttachHandler(evt->{
			table = new SpreadSheetTable(contents.getElement(), setting);
		});
	}
	
	public SpreadSheet update(SheetSetting setting) {
		if(table!=null) table.updateSettings(setting);
		return this;
	}
	
	public SheetSetting getSetting() {
		if(table==null) return setting;
		return table.getSettings();
	}
	
	public SpreadSheet render() {
		if(table!=null) table.render();
		return this;
	}
	
	@Override
	public void onResize() {
		super.onResize();
		render();
	}
	
	public Element getCell(int row, int col, boolean topmost) {
		return table.getCell(row, col, topmost);
	}
	
	public Element[] getCells(int row, boolean topmost) {
		int length = table.getSettings().getColHeaders().length;
		return IntStream.range(0, length).mapToObj(col->getCell(row, col, topmost)).toArray(Element[]::new);
	}
	
	public int countRows() {
		return table.countRows();
	}
	
	public int countCols() {
		return table.countCols();
	}
	
	public boolean selectColumns(int start, int end) {
		return table.selectColumns(start, end);
	}
	
	public boolean selectColumn(int start) {
		return table.selectColumns(start);
	}
	
	public boolean selectRows(int start, int end) {
		return table.selectRows(start, end);
	}
	
	public boolean selectRow(int start) {
		return table.selectRows(start);
	}
	
	public boolean selectCell(int row, int column) {
		return table.selectCell(row, column);
	}
	
	public void insertAfter(int row, Data[] values) {
		
	}
	
	@JsType(isNative = true, namespace = JsPackage.GLOBAL, name="Handsontable")	
	public final static class SpreadSheetTable {
		public SpreadSheetTable(Element element, SheetSetting setting) {};
		public native void render();
		public native void updateSettings(SheetSetting setting);
		public native SheetSetting getSettings();
		public native int countRows();
		public native int countCols();
		public native boolean selectColumns(int start, int end);
		public native boolean selectColumns(int start);
		public native boolean selectRows(int start, int end);
		public native boolean selectRows(int start);
		public native boolean selectCell(int row, int column);
		public native Element getCell(int row, int col, boolean topmost);
		public native String getDataAtCell(int row, int column);
		public native BaseEditor getActiveEditor();
		public native void alter(String action, int idex, int amount);
	}
	
	@JsFunction
	public static interface AfterInit {
		void exec(boolean isFirst);
	}
	
	@JsFunction
	public static interface AfterRender {
		void exec(boolean isForced);
	}
	
	@JsFunction
	public static interface Callback<T> {
		void exec(T t);
	}
	
	@JsFunction
	public static interface AutoComplete {
		void exec(String query, Callback<String[]> callback);
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Filter {
		@JsProperty(name="column")
		private Integer column;
		@JsProperty(name="conditions")
		private FilterCondition[] conditions;
		@JsProperty(name="operation")
		private String operation;
		@JsOverlay
		public Integer getColumn() {
			return column;
		}
		@JsOverlay
		public FilterCondition[] getConditions() {
			return conditions;
		}
		@JsOverlay
		public String getOperation() {
			return operation;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class FilterCondition {
		@JsProperty(name="name")
		private String name;
		@JsProperty(name="args")
		private String[][] args;
		@JsOverlay
		public String getName() {
			return name;
		}
		@JsOverlay
		public String[][] getArgs() {
			return args;
		}
	}
	@JsFunction
	public static interface AfterFilter {
		void apply(Filter[] conditionsStack);
	}
	
	@JsFunction
	public static interface Validator {
		void exec(Object value, Callback<Boolean> callback);
	}

	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class ColumnInfo {
		@JsProperty(name="data")
		private String data;
		@JsProperty(name="type")
		private String type;
		@JsProperty(name="_width")
		private Integer width;
		@JsProperty(name="format")
		private String format;
		@JsProperty(name="dateFormat")
		private String dateFormat;
		@JsProperty(name="source")
		private Object source;
		@JsProperty(name="strict")
		private boolean strict;
		@JsProperty(name="readOnly")
		private boolean readOnly;
		@JsProperty(name="renderer")
		private Object renderer;
		@JsProperty(name="validator")
		private Validator validator;
		@JsProperty(name="allowInvalid")
		private boolean allowInvalid;
		@JsProperty(name="allowEmpty")
		private boolean allowEmpty;
		@JsProperty(name="editor")
		private PrototypeEditor<?> editor;
		@JsProperty(name="filter")
		private boolean filter;
		@JsOverlay
		public String getData() {
			return data;
		}
		@JsOverlay
		public ColumnInfo setData(String data) {
			this.data = data;
			return this;
		}
		@JsOverlay
		public String getType() {
			return type;
		}
		@JsOverlay
		public ColumnInfo setType(String type) {
			this.type = type;
			if("autocomplete".equalsIgnoreCase(type)) setValidator((value, callback)->{
				if(this.isAllowInvalid()) callback.exec(true);
				else if(value == null || "".equals(value)){
					if(this.isAllowEmpty()) callback.exec(true);
					else callback.exec(false);
				} else {
					if(source instanceof String[]) {
						String[] sources = (String[])source;
						if(Arrays.stream(sources).anyMatch(s->s.equals(value))) callback.exec(true);
						else callback.exec(false);
					} else if(source instanceof AutoComplete) {
						Callback<String[]> callback2 = list->{
							if(Arrays.stream(list).anyMatch(s->s.equals(value))) callback.exec(true);
							else callback.exec(false);
						};
						((AutoComplete)source).exec((String)value, callback2);
					}
				}
			}); else if("dropdown".equalsIgnoreCase(type)) setValidator((value, callback)->{
				if(this.isAllowInvalid()) callback.exec(true);
				else if(value == null || "".equals(value)){
					if(this.isAllowEmpty()) callback.exec(true);
					else callback.exec(false);
				} else {
					String[] sources = (String[])source;
					if(Arrays.stream(sources).anyMatch(s->s.equals(value))) callback.exec(true);
					else callback.exec(false);
				}
			}); else if("numeric".equalsIgnoreCase(type)) setValidator((value, callback)->{
				try {
					if(value == null || "".equals(value)) {
						if(this.isAllowEmpty()) callback.exec(true);
						else callback.exec(false);
					} else {
						NumberFormat nf = NumberFormat.getFormat(format);
						nf.format((Number)value);
						callback.exec(true);
					}
				} catch(Exception e) {
					callback.exec(false);
				}
			}); else if("date".equalsIgnoreCase(type)) setValidator((value, callback)->{
				try {
					if(value == null || "".equals(value)) {
						if(this.isAllowEmpty()) callback.exec(true);
						else callback.exec(false);
					} else {
					//	DateTimeFormat dtf = DateTimeFormat.getFormat(dateFormat);
					//	dtf.parse((String)value);
						callback.exec(true);
					}
				} catch(Exception e) {
					callback.exec(false);
				}
			}); else GWT.log(type);
			return this;
		}
		@JsOverlay
		public Integer getWidth() {
			return width;
		}
		@JsOverlay
		public ColumnInfo setWidth(Integer width) {
			this.width = width;
			return this;
		}
		@JsOverlay
		public String getFormat() {
			return format;
		}
		@JsOverlay
		public ColumnInfo setFormat(String format) {
			this.format = format;
			return this;
		}
		@JsOverlay
		public String getDateFormat() {
			return dateFormat;
		}
		@JsOverlay
		public ColumnInfo setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
			return this;
		}
		@JsOverlay
		public Object getSource() {
			return source;
		}
		@JsOverlay
		public ColumnInfo setSource(String[] source) {
			this.source = source;
			return this;
		}
		@JsOverlay
		public ColumnInfo setSource(AutoComplete source) {
			this.source = source;
			return this;
		}
		@JsOverlay
		public boolean isStrict() {
			return strict;
		}
		@JsOverlay
		public ColumnInfo setStrict(boolean strict) {
			this.strict = strict;
			return this;
		}
		@JsOverlay
		public boolean isReadOnly() {
			return readOnly;
		}
		@JsOverlay
		public ColumnInfo setReadOnly(boolean readOnly) {
			this.readOnly = readOnly;
			return this;
		}
		@JsOverlay
		public Object getRenderer() {
			return renderer;
		}
		@JsOverlay
		public ColumnInfo setRenderer(String renderer) {
			this.renderer = renderer;
			return this;
		}
		@JsOverlay
		public ColumnInfo setRenderer(Renderer renderer) {
			this.renderer = new Renderer() {
				@Override
				public Element render(SpreadSheetTable instance, Element td, int row, int col, String prop, Object value, ColumnInfo columnInfo) {
					baseRenderer(this, instance, td, row, col, prop, value, columnInfo);
					if(columnInfo.getValidator()!=null) try {
						columnInfo.getValidator().exec(value, test->{
							Object tested = value;
							if(!test) {
								tested = null;
								td.addClassName(StyleChart.GSS.errorRow());
								String type = columnInfo.getType(); 
								if(!columnInfo.isReadOnly() && ("dropdown".equals(type) || "autocomplete".equals(type))) {
									renderer.render(instance, td, row, col, prop, tested, columnInfo);
									DivElement arrow = Document.get().createDivElement();
									arrow.setInnerHTML("▼");
									arrow.setClassName("htAutocompleteArrow");
									Event.sinkEvents(arrow, Event.ONCLICK);
									Event.setEventListener(arrow, evt->{
										BaseEditor editor = instance.getActiveEditor();
										String data = instance.getDataAtCell(row, col);
										if(data!=null) {
											data = data.trim();
											editor.beginEditing(data);
											editor.setValue(data);
										} else editor.beginEditing("");
										evt.preventDefault();
										evt.stopPropagation();
									});
									td.getStyle().setDisplay(Display.TABLE_CELL);
									td.getStyle().setWhiteSpace(WhiteSpace.PRE);
									td.appendChild(arrow);
								} else renderer.render(instance, td, row, col, prop, tested, columnInfo);
							}
						});
					} catch(Exception e) {
						td.addClassName(StyleChart.GSS.errorRow());
						String type = columnInfo.getType(); 
						if(!columnInfo.isReadOnly() && ("dropdown".equals(type) || "autocomplete".equals(type))) {
							renderer.render(instance, td, row, col, prop, null, columnInfo);
							DivElement arrow = Document.get().createDivElement();
							arrow.setInnerHTML("▼");
							arrow.setClassName("htAutocompleteArrow");
							Event.sinkEvents(arrow, Event.ONCLICK);
							Event.setEventListener(arrow, evt->{
								BaseEditor editor = instance.getActiveEditor();
								String data = instance.getDataAtCell(row, col);
								if(data!=null) {
									data = data.trim();
									editor.beginEditing(data);
									editor.setValue(data);
								} else editor.beginEditing("");
								evt.preventDefault();
								evt.stopPropagation();
							});
							td.getStyle().setDisplay(Display.TABLE_CELL);
							td.getStyle().setWhiteSpace(WhiteSpace.PRE);
							td.appendChild(arrow);
						} else renderer.render(instance, td, row, col, prop, null, columnInfo);
					}
					String type = columnInfo.getType(); 
					if(!columnInfo.isReadOnly() && ("dropdown".equals(type) || "autocomplete".equals(type))) {
						renderer.render(instance, td, row, col, prop, value, columnInfo);
						DivElement arrow = Document.get().createDivElement();
						arrow.setInnerHTML("▼");
						arrow.setClassName("htAutocompleteArrow");
						Event.sinkEvents(arrow, Event.ONCLICK);
						Event.setEventListener(arrow, evt->{
							BaseEditor editor = instance.getActiveEditor();
							String data = instance.getDataAtCell(row, col);
							if(data!=null) {
								data = data.trim();
								editor.beginEditing(data);
								editor.setValue(data);
							} else editor.beginEditing("");
							evt.preventDefault();
							evt.stopPropagation();
						});
						td.getStyle().setDisplay(Display.TABLE_CELL);
						td.getStyle().setWhiteSpace(WhiteSpace.PRE);
						td.appendChild(arrow);
					} else renderer.render(instance, td, row, col, prop, value, columnInfo);
					return td;
				}
			};
			registerRenderer(this.renderer, "RND" + Random.nextInt());
			return this;
		}
		@JsOverlay
		public Validator getValidator() {
			return validator;
		}
		@JsOverlay
		public ColumnInfo setValidator(Validator validator) {
			this.validator = validator;
			return this;
		}
		@JsOverlay
		public boolean isAllowInvalid() {
			return allowInvalid;
		}
		@JsOverlay
		public ColumnInfo setAllowInvalid(boolean allowInvalid) {
			this.allowInvalid = allowInvalid;
			return this;
		}
		@JsOverlay
		public boolean isAllowEmpty() {
			return allowEmpty;
		}
		@JsOverlay
		public ColumnInfo setAllowEmpty(boolean allowEmpty) {
			this.allowEmpty = allowEmpty;
			return this;
		}
		@JsOverlay
		public PrototypeEditor<?> getEditor() {
			return editor;
		}
		@JsOverlay
		public ColumnInfo setEditor(PrototypeEditor<?> editor) {
			this.editor = editor;
			return this;
		}
		@JsOverlay
		public boolean isFilter() {
			return filter;
		}
		@JsOverlay
		public ColumnInfo setFilter(boolean filter) {
			this.filter = filter;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Data {
		@JsProperty(name="origin")
		private Object origin;
		
		@JsOverlay
		public Data put(String key, Object item) {
			if(item!=null && (item instanceof String)) item = ((String)item).trim();
			SpreadSheet.put(this, key, item);
			return this;
		}
		@JsOverlay
		public <T> T get(String key) {
			return SpreadSheet.get(this, key);
		}
		
		@JsOverlay
		public Data setOrigin(String key, Object item) {
			if(origin == null) origin = new Object();
			SpreadSheet.put(origin, key, item);
			SpreadSheet.put(this, key, item);
			return this;
		}
		
		@JsOverlay
		public boolean isChanged(String key) {
			if(this.origin == null) this.origin = new Object();
			Object origin = SpreadSheet.get(this.origin, key);
			Object current = get(key);
			if(origin instanceof String) {
				String cast = (String)origin;
				if(cast!=null) cast = cast.trim();
				if(cast.isEmpty()) origin = null;
				else origin = cast;
			}
			if(current instanceof String) {
				String cast = (String)current;
				if(cast!=null) cast = cast.trim();
				if(cast.isEmpty()) current = null;
				else current = cast;
			}
			if(origin == null && current == null) return false;
			if(origin == null) return true;
			if(current == null) return true;
			return !origin.equals(current);
		}
	}
	private static native <T> T put(T data, String property, Object value) /*-{
		data[property] = value;
		return this;
	}-*/;
	public static native <T> T get(Object data, String property) /*-{
		return data[property];
	}-*/;
	public static native <T> T push(T array, Object data) /*-{
		array.push(data);
		return array;
	}-*/;
	
	@JsFunction
	public static interface Change {
		void change(String[][] info, String source);
	}
	
	@JsFunction
	public static interface ChangeProxy {
		void change(int row, String prop, String old, String next, String source);
	}
	
	@JsFunction
	public static interface Renderer {
		Element render(SpreadSheetTable instance, Element td, int row, int col, String prop, Object value, ColumnInfo columnInfo);
		@JsOverlay
		default String getFont() {
			return "Noto Sans KR";
		}
		@JsOverlay
		default int getFontSize() {
			return 12;
		}
	}
	
	@JsFunction
	public static interface AfterOnCellMouseDown {
		void apply(Event event, Coordinator coords, Element td);
	}
	
	@JsFunction
	public static interface AfterSelection {
		void apply(int startRow, int startCol, int endRow, int endCol);
	}
	
	@JsFunction
	public static interface AfterGetColHeader {
		void apply(int col, Element th);
	}
	
	@JsFunction
	public static interface BeforeKeyDown {
		void apply(Event event);
	}
	
	@JsFunction
	public static interface AfterKeyDown {
		void apply(Event event);
	}
	
	@JsFunction
	public static interface BeforeColumnMove {
		void apply(int[] targets, int dest);
	}
	
	@JsFunction
	public static interface AfterColumnMove {
		void apply(int[] targets, int dest);
	}
	
	@JsFunction
	public static interface BeforePaste {
		void apply(Object[][] data, Object[] coords);
	}
	
	@JsFunction
	public static interface AfterPaste {
		void apply(Object[][] data, Object[] coords);
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Coordinator {
		@JsProperty(name="row")
		private Double row;
		@JsProperty(name="col")
		private Double col;
		@JsOverlay
		public Integer getRow() {
			return row!=null?row.intValue():null;
		}
		@JsOverlay
		public Coordinator setRow(Integer row) {
			this.row = row!=null?row.doubleValue():null;
			return this;
		}
		@JsOverlay
		public Integer getCol() {
			return col!=null?col.intValue():null;
		}
		@JsOverlay
		public Coordinator setCol(Integer col) {
			this.col = col!=null?col.doubleValue():null;
			return this;
		}
	}
	
	@JsFunction
	public static interface ColWidths {
		Integer getColWidth(int idx);
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class ContextMenu {
		@JsProperty(name="items")
		private Object[] items;
		@JsProperty(name="callback")
		private Callback<String> callback;
		@JsOverlay
		public Callback<String> getCallback() {
			return callback;
		}
		@JsOverlay
		public ContextMenu setCallback(Callback<String> callback) {
			this.callback = callback;
			return this;
		}
		@JsOverlay
		public ContextMenu addItem(ContextMenuItem item) {
			if(items == null) items = new Object[0];
			push(items, item);
			return this;
		}
		
		@JsOverlay
		public ContextMenu addItem(ContextMenuSub item) {
			if(items == null) items = new Object[0];
			push(items, item);
			return this;
		}
		
		@JsOverlay
		public ContextMenu addItem(String key, String name) {
			return addItem(new ContextMenuItem().setKey(key).setLabel(name));
		}
		
		@JsOverlay
		public ContextMenu addSubMenu(String key, String name, ContextMenu sub) {
			return addItem(new ContextMenuSub().setKey(key).setLabel(name).setSubMenu(sub));
		}
		
		@JsOverlay
		public ContextMenu addItem(String key, Element elem) {
			return addItem(new ContextMenuItem().setKey(key).setElement(elem));
		}
		
		@JsOverlay
		public ContextMenu addSubMenu(String key, Element elem, ContextMenu sub) {
			return addItem(new ContextMenuSub().setKey(key).setElement(elem).setSubMenu(sub));
		}
		
		@JsOverlay
		public ContextMenu addDevider() {
			if(items == null) items = new ContextMenuItem[0];
			addItem("sep_" + items.length, "---------");
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class ContextMenuItem {
		@JsProperty(name="key")
		private String key;
		@JsProperty(name="disabled")
		private CheckFunction disabled;
		@JsProperty(name="name")
		private Object name;
		@JsProperty(name="callback")
		private Callback<String> callback;
		@JsOverlay
		public String getKey() {
			return key;
		}
		@JsOverlay
		public ContextMenuItem setKey(String key) {
			this.key = key;
			return this;
		}
		@JsOverlay
		public CheckFunction getDisabled() {
			return disabled;
		}
		@JsOverlay
		public ContextMenuItem setDisabled(CheckFunction disabled) {
			this.disabled = disabled;
			return this;
		}
		@JsOverlay
		public Object getName() {
			return name;
		}
		@JsOverlay
		public ContextMenuItem setLabel(String name) {
			this.name = name;
			return this;
		}
		@JsOverlay
		public ContextMenuItem setElement(Element name) {
			this.name = name.getString();
			return this;
		}
		@JsOverlay
		public Callback<String> getCallback() {
			return callback;
		}
		@JsOverlay
		public ContextMenuItem setCallback(Callback<String> callback) {
			this.callback = callback;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class ContextMenuSub {
		@JsProperty(name="key")
		private String key;
		@JsProperty(name="disabled")
		private CheckFunction disabled;
		@JsProperty(name="name")
		private String name;
		@JsProperty(name="submenu")
		private ContextMenu submenu;
		@JsOverlay
		public String getKey() {
			return key;
		}
		@JsOverlay
		public ContextMenuSub setKey(String key) {
			this.key = key;
			return this;
		}
		@JsOverlay
		public CheckFunction getDisabled() {
			return disabled;
		}
		@JsOverlay
		public ContextMenuSub setDisabled(CheckFunction disabled) {
			this.disabled = disabled;
			return this;
		}
		@JsOverlay
		public String getName() {
			return name;
		}
		@JsOverlay
		public ContextMenuSub setLabel(String name) {
			this.name = name;
			return this;
		}
		@JsOverlay
		public ContextMenuSub setElement(Element elem) {
			this.name = elem.getString();
			return this;
		}
		@JsOverlay
		public ContextMenu getSubMenu() {
			return submenu;
		}
		@JsOverlay
		public ContextMenuSub setSubMenu(ContextMenu submenu) {
			this.submenu = submenu;
			return this;
		}
	}
	
	@JsFunction
	public interface CheckFunction {
		boolean check();
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class SheetSetting {
		@JsProperty(name="data")
		private Data[] data;
		@JsProperty(name="licenseKey")
		private String licenseKey;
		@JsProperty(name="stretchH")
		private String stretchH;
		@JsProperty(name="width")
		private Integer width;
		@JsProperty(name="height")
		private Integer height;
		@JsProperty(name="minRows")
		private Integer minRows;
		@JsProperty(name="maxRows")
		private Integer maxRows;
		@JsProperty(name="fixedRowsTop")
		private Integer fixedRowsTop;
		@JsProperty(name="fixedColumnsLeft")
		private Integer fixedColumnsLeft;
		@JsProperty(name="rowHeaders")
		private boolean rowHeaders;
		@JsProperty(name="manualRowResize")
		private boolean manualRowResize;
		@JsProperty(name="manualColumnResize")
		private boolean manualColumnResize;
		@JsProperty(name="manualRowMove")
		private boolean manualRowMove;
		@JsProperty(name="manualColumnMove")
		private boolean manualColumnMove;
		@JsProperty(name="contextMenu")
		private Object contextMenu;
		@JsProperty(name="dropdownMenu")
		private Object dropdownMenu;
		@JsProperty(name="filters")
		private boolean filters;
		@JsProperty(name="autoRowSize")
		private boolean autoRowSize;
		@JsProperty(name="autoColSize")
		private boolean autoColSize;
		@JsProperty(name="columns")
		private ColumnInfo[] columns;
		@JsProperty(name="colHeaders")
		private String[] colHeaders;
		@JsProperty(name="formulas")
		private boolean formulas;
		@JsProperty(name="preventOverflow")
		private String preventOverflow;
		@JsProperty(name="disableVisualSelection")
		private boolean disableVisualSelection;
		@JsProperty(name="colWidths")
		private ColWidths colWidths;
		@JsProperty(name="afterDocumentKeyDown")
		private AfterKeyDown afterKeyDown;
		@JsProperty(name="afterKeyDown")
		private BeforeKeyDown beforeKeyDown;
		@JsProperty(name="beforeChange")
		private Change beforeChange;
		@JsProperty(name="afterChange")
		private Change afterChange;
		@JsProperty(name="afterInit")
		private AfterInit afterInit;
		@JsProperty(name="afterRender")
		private AfterRender afterRender;
		@JsProperty(name="afterOnCellMouseDown")
		private AfterOnCellMouseDown afterOnCellMouseDown;
		@JsProperty(name="afterSelection")
		private AfterSelection afterSelection;
		@JsProperty(name="afterGetColHeader")
		private AfterGetColHeader afterGetColHeader;
		@JsProperty(name="beforeColumnMove")
		private BeforeColumnMove beforeColumnMove;
		@JsProperty(name="afterColumnMove")
		private AfterColumnMove afterColumnMove;
		@JsProperty(name="beforePaste")
		private BeforePaste beforePaste;
		@JsProperty(name="afterPaste")
		private AfterPaste afterPaste;
		@JsProperty(name="afterFilter")
		private AfterFilter afterFilter;
		@JsOverlay
		public Data[] getData() {
			return data;
		}
		
		@JsOverlay
		public Data[] addData(Data item) {
			if(data == null) data = new Data[] {};
			push(data, item);
			return data;
		}
		
		@JsOverlay
		public SheetSetting setData(Data[] data) {
			this.data = data;
			return this;
		}

		@JsOverlay
		public String getLicenseKey() {
			return licenseKey;
		}

		@JsOverlay
		public SheetSetting setLicenseKey(String licenseKey) {
			this.licenseKey = licenseKey;
			return this;
		}

		@JsOverlay
		public String getStretchH() {
			return stretchH;
		}

		@JsOverlay
		public SheetSetting setStretchH(String stretchH) {
			this.stretchH = stretchH;
			return this;
		}

		@JsOverlay
		public Integer getWidth() {
			return width;
		}

		@JsOverlay
		public SheetSetting setWidth(Integer width) {
			this.width = width;
			return this;
		}

		@JsOverlay
		public Integer getHeight() {
			return height;
		}

		@JsOverlay
		public SheetSetting setHeight(Integer height) {
			this.height = height;
			return this;
		}

		@JsOverlay
		public Integer getMinRows() {
			return minRows;
		}

		@JsOverlay
		public SheetSetting setMinRows(Integer minRows) {
			this.minRows = minRows;
			return this;
		}

		@JsOverlay
		public Integer getMaxRows() {
			return maxRows;
		}

		@JsOverlay
		public SheetSetting setMaxRows(Integer maxRows) {
			this.maxRows = maxRows;
			return this;
		}

		@JsOverlay
		public Integer getFixedRowsTop() {
			return fixedRowsTop;
		}

		@JsOverlay
		public SheetSetting setFixedRowsTop(Integer fixedRowsTop) {
			this.fixedRowsTop = fixedRowsTop;
			return this;
		}

		@JsOverlay
		public Integer getFixedColumnsLeft() {
			return fixedColumnsLeft;
		}

		@JsOverlay
		public SheetSetting setFixedColumnsLeft(Integer fixedColumnsLeft) {
			this.fixedColumnsLeft = fixedColumnsLeft;
			return this;
		}

		@JsOverlay
		public String[] getColHeaders() {
			return colHeaders;
		}

		@JsOverlay
		public SheetSetting setColHeaders(String[] colHeaders) {
			this.colHeaders = colHeaders;
			return this;
		}
		
		@JsOverlay
		public boolean isRowHeaders() {
			return rowHeaders;
		}

		@JsOverlay
		public SheetSetting setRowHeaders(boolean rowHeaders) {
			this.rowHeaders = rowHeaders;
			return this;
		}

		@JsOverlay
		public boolean isManualRowResize() {
			return manualRowResize;
		}

		@JsOverlay
		public SheetSetting setManualRowResize(boolean manualRowResize) {
			this.manualRowResize = manualRowResize;
			return this;
		}

		@JsOverlay
		public boolean isManualColumnResize() {
			return manualColumnResize;
		}

		@JsOverlay
		public SheetSetting setManualColumnResize(boolean manualColumnResize) {
			this.manualColumnResize = manualColumnResize;
			return this;
		}

		@JsOverlay
		public boolean isManualRowMove() {
			return manualRowMove;
		}

		@JsOverlay
		public SheetSetting setManualRowMove(boolean manualRowMove) {
			this.manualRowMove = manualRowMove;
			return this;
		}

		@JsOverlay
		public boolean isManualColumnMove() {
			return manualColumnMove;
		}

		@JsOverlay
		public SheetSetting setManualColumnMove(boolean manualColumnMove) {
			this.manualColumnMove = manualColumnMove;
			return this;
		}

		@JsOverlay
		public SheetSetting setContextMenu(boolean contextMenu) {
			this.contextMenu = contextMenu;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setContextMenu(ContextMenu contextMenu) {
			this.contextMenu = contextMenu;
			return this;
		}

		@SuppressWarnings("unchecked")
		@JsOverlay
		public <T> T getDropdownMenu() {
			return (T)dropdownMenu;
		}

		@JsOverlay
		public SheetSetting setDropdownMenu(boolean dropdownMenu) {
			this.dropdownMenu = dropdownMenu;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setDropdownMenu(String... dropdownMenu) {
			this.dropdownMenu = dropdownMenu;
			return this;
		}

		@JsOverlay
		public boolean isFilters() {
			return filters;
		}

		@JsOverlay
		public SheetSetting setFilters(boolean filters) {
			this.filters = filters;
			return this;
		}

		@JsOverlay
		public boolean isAutoRowSize() {
			return autoRowSize;
		}

		@JsOverlay
		public SheetSetting setAutoRowSize(boolean autoRowSize) {
			this.autoRowSize = autoRowSize;
			return this;
		}

		@JsOverlay
		public boolean isAutoColSize() {
			return autoColSize;
		}

		@JsOverlay
		public SheetSetting setAutoColSize(boolean autoColSize) {
			this.autoColSize = autoColSize;
			return this;
		}

		@JsOverlay
		public ColumnInfo[] getColumns() {
			return columns;
		}

		@JsOverlay
		public SheetSetting setColumns(ColumnInfo... columns) {
			this.columns = columns;
			List<String> headers = Arrays.stream(columns)
			.map(col->col.getData())
			.collect(Collectors.toList());
			String[] arr = new String[headers.size()];
			headers.toArray(arr);
			setColHeaders(arr);
			return this;
		}
		
		@JsOverlay
		public boolean getFormulas() {
			return formulas;
		}

		@JsOverlay
		public SheetSetting setFormulas(boolean formulas) {
			this.formulas = formulas;
			return this;
		}

		@JsOverlay
		public SheetSetting preventOverflowX() {
			this.preventOverflow = "horizontal";
			return this;
		}

		@JsOverlay
		public Change getAfterChange() {
			return afterChange;
		}

		@JsOverlay
		public SheetSetting setAfterChange(Change change) {
			this.afterChange = change;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setAfterChange(ChangeProxy change) {
			this.afterChange = (changes, source)->{
				if(changes!=null) for(String[] ch: changes) if(ch!=null) change.change(Integer.parseInt(ch[0]), ch[1], ch[2], ch[3], source);
			};
			return this;
		}
		
		@JsOverlay
		public Change getBeforeChange() {
			return beforeChange;
		}

		@JsOverlay
		public SheetSetting setBeforeChange(Change change) {
			this.beforeChange = change;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setBeforerChange(ChangeProxy change) {
			this.beforeChange = (changes, source)->{
				if(changes!=null) for(String[] ch: changes) if(ch!=null) change.change(Integer.parseInt(ch[0]), ch[1], ch[2], ch[3], source);
			};
			return this;
		}

		@JsOverlay
		public AfterInit getAfterInit() {
			return afterInit;
		}

		@JsOverlay
		public SheetSetting setAfterInit(AfterInit afterInit) {
			this.afterInit = afterInit;
			return this;
		}

		@JsOverlay
		public AfterRender getAfterRender() {
			return afterRender;
		}

		@JsOverlay
		public SheetSetting setAfterRender(AfterRender afterRender) {
			this.afterRender = afterRender;
			return this;
		}

		@JsOverlay
		public ColWidths getColWidths() {
			return colWidths;
		}
		
		@JsOverlay
		public BeforeKeyDown getBeforeKeyDown() {
			return beforeKeyDown;
		}

		@JsOverlay
		public SheetSetting setBeforeKeyDown(BeforeKeyDown beforeKeyDown) {
			this.beforeKeyDown = beforeKeyDown;
			return this;
		}
		
		@JsOverlay
		public AfterKeyDown getAfterKeyDown() {
			return afterKeyDown;
		}

		@JsOverlay
		public SheetSetting setAfterKeyDown(AfterKeyDown afterKeyDown) {
			this.afterKeyDown = afterKeyDown;
			return this;
		}

		@JsOverlay
		public SheetSetting setColWidths(ColWidths colWidths) {
			this.colWidths = colWidths;
			return this;
		}
		
		@JsOverlay
		public AfterOnCellMouseDown getAfterOnCellMouseDown() {
			return afterOnCellMouseDown;
		}

		@JsOverlay
		public SheetSetting setAfterOnCellMouseDown(AfterOnCellMouseDown afterOnCellMouseDown) {
			this.afterOnCellMouseDown = afterOnCellMouseDown;
			return this;
		}
		
		@JsOverlay
		public AfterSelection getAfterSelection() {
			return afterSelection;
		}

		@JsOverlay
		public SheetSetting setAfterSelection(AfterSelection afterSelection) {
			this.afterSelection = afterSelection;
			return this;
		}

		@JsOverlay
		public AfterGetColHeader getAfterGetColHeader() {
			return afterGetColHeader;
		}

		@JsOverlay
		public SheetSetting setAfterGetColHeader(AfterGetColHeader afterGetColHeader) {
			this.afterGetColHeader = afterGetColHeader;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setBeforeColumnMove(BeforeColumnMove beforeColumnMove) {
			this.beforeColumnMove = beforeColumnMove;
			return this;
		}
		
		@JsOverlay
		public SheetSetting setAfterColumnMove(AfterColumnMove afterColumnMove) {
			this.afterColumnMove = afterColumnMove;
			return this;
		}

		@JsOverlay
		public SheetSetting setBeforePaste(BeforePaste beforePaste) {
			this.beforePaste = beforePaste;
			return this;
		}

		@JsOverlay
		public SheetSetting setAfterPaste(AfterPaste afterPaste) {
			this.afterPaste = afterPaste;
			return this;
		}

		@JsOverlay
		public AfterFilter getAfterFilter() {
			return afterFilter;
		}

		@JsOverlay
		public SheetSetting setAfterFilter(AfterFilter afterFilter) {
			this.afterFilter = afterFilter;
			return this;
		}
	}
	
	private static native <T> void push(T[] data, T item) /*-{
		data.push(item);
	}-*/;
	
	@JsFunction
	public static interface EditorCreateElement {
		Element create();
	}
	
	@JsFunction
	public static interface EditorPrepare {
		void apply(Element elem);
	}
	
	@JsFunction
	public static interface EditorOpen {
		void apply(Element elem);
	}
	
	@JsFunction
	public static interface EditorSetValue<T> {
		void setValue(Element elem, T value);
	}
	
	@JsFunction
	public static interface EditorGetValue<T> {
		T getValue(Element elem);
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class Prototype<T> {
		@JsProperty(name="create")
		private EditorCreateElement create;
		@JsProperty(name="prepare_")
		private EditorPrepare prepare;
		@JsProperty(name="open_")
		private EditorOpen open;
		@JsProperty(name="getValue_")
		private EditorGetValue<T> getValue;
		@JsProperty(name="setValue_")
		private EditorSetValue<T> setValue;
		@JsProperty(name="focus_")
		private EditorOpen focus;

		@JsOverlay
		public Prototype<T> setCreate(EditorCreateElement create) {
			this.create = create;
			return this;
		}

		@JsOverlay
		public Prototype<T> setPrepare(EditorPrepare prepare) {
			this.prepare = prepare;
			return this;
		}
		
		@JsOverlay
		public Prototype<T> setOpen(EditorOpen open) {
			this.open = open;
			return this;
		}

		@JsOverlay
		public Prototype<T> setGetValue(EditorGetValue<T> getValue) {
			this.getValue = getValue;
			return this;
		}

		@JsOverlay
		public Prototype<T> setSetValue(EditorSetValue<T> setValue) {
			this.setValue = setValue;
			return this;
		}

		@JsOverlay
		public Prototype<T> setFocus(EditorOpen focus) {
			this.focus = focus;
			return this;
		}
	}
	
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class PrototypeEditor<T> {
		@JsProperty(name="prototype")
		private Prototype<T> prototype;
		
		@JsOverlay
		public Prototype<T> getPrototype() {
			return prototype;
		}

		@JsOverlay
		public PrototypeEditor<T> setPrototype(Prototype<T> prototype) {
			this.prototype = prototype;
			return this;
		}
	}
	@JsType(isNative = true, namespace= JsPackage.GLOBAL, name="Object")
	public final static class BaseEditor {
		public native void enableFullEditMode();
		public native boolean isInFullEditMode();
		public native void beginEditing(String value);
		public native void setValue(String value);
	}
	public static native void registerRenderer(Object renderer, String id) /*-{
		$wnd.Handsontable.renderers.registerRenderer(id, renderer);
	}-*/;
	public static native void baseRenderer(Object renderer, Object... args) /*-{
		$wnd.Handsontable.renderers.BaseRenderer.apply(renderer, args);
	}-*/;
	
	public static native <T> PrototypeEditor<T> createBaseEditor(Class<T> clazz) /*-{
		var tmp = $wnd.Handsontable.editors.BaseEditor.prototype.extend();
		tmp.prototype.init=function() {
			this.element = tmp.prototype.create();
			this.element.style.position='absolute';
			this.element.style.margin = '0px';
			this.element.style.padding = '0px';
			this.instance.rootElement.appendChild(this.element);
		}
		tmp.prototype.prepare=function() {
			$wnd.Handsontable.editors.BaseEditor.prototype.prepare.apply(this, arguments);
			$wnd.Handsontable.dom.empty(this.element);
			try {
				tmp.prototype.prepare_(this.element);
			} catch(ignore){};
		}
		tmp.prototype.open=function() {
			var keys = Object.keys($wnd.Handsontable);
			var width = $wnd.Handsontable.dom.outerWidth(this.TD);
			var height = $wnd.Handsontable.dom.outerHeight(this.TD);
			var rootOffset = $wnd.Handsontable.dom.offset(this.instance.rootElement);
			var tdOffset = $wnd.Handsontable.dom.offset(this.TD);
			var scrollTop = this.instance.view.wt.wtTable.holder.scrollTop;
			var scrollLeft = this.instance.view.wt.wtTable.holder.scrollLeft;
			this.element.style.height = height + 'px';
			this.element.style.minWidth = width + 'px';
			this.element.style.top = tdOffset.top - scrollTop - rootOffset.top + 'px';
			this.element.style.left = tdOffset.left - scrollLeft - rootOffset.left + 'px';
			this.element.style.display = '';
			try {
				tmp.prototype.open_(this.element);
			} catch(ignore){};
		}
		tmp.prototype.close=function() {
			this.element.style.display = 'none';
		}
		tmp.prototype.focus=function() {
			if(tmp.prototype.focus_!=null) tmp.prototype.focus_(this.element);
			else try {
				this.element.focus();
			} catch(ignore){};
		}
		tmp.prototype.getValue=function() {
			return tmp.prototype.getValue_(this.element);
		}
		tmp.prototype.setValue=function(value) {
			tmp.prototype.setValue_(this.element, value);
		}
		return tmp;
	}-*/;
}
