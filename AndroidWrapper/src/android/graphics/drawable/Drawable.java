/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.graphics.drawable;


import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * A Drawable is a general abstraction for "something that can be drawn."  Most
 * often you will deal with Drawable as the type of resource retrieved for
 * drawing things to the screen; the Drawable class provides a generic API for
 * dealing with an underlying visual resource that may take a variety of forms.
 * Unlike a {@link android.view.View}, a Drawable does not have any facility to
 * receive events or otherwise interact with the user.
 *
 * <p>In addition to simple drawing, Drawable provides a number of generic
 * mechanisms for its client to interact with what is being drawn:
 *
 * <ul>
 *     <li> The {@link #setBounds} method <var>must</var> be called to tell the
 *     Drawable where it is drawn and how large it should be.  All Drawables
 *     should respect the requested size, often simply by scaling their
 *     imagery.  A client can find the preferred size for some Drawables with
 *     the {@link #getIntrinsicHeight} and {@link #getIntrinsicWidth} methods.
 *
 *     <li> The {@link #getPadding} method can return from some Drawables
 *     information about how to frame content that is placed inside of them.
 *     For example, a Drawable that is intended to be the frame for a button
 *     widget would need to return padding that correctly places the label
 *     inside of itself.
 *
 *     <li> The {@link #setState} method allows the client to tell the Drawable
 *     in which state it is to be drawn, such as "focused", "selected", etc.
 *     Some drawables may modify their imagery based on the selected state.
 *
 *     <li> The {@link #setLevel} method allows the client to supply a single
 *     continuous controller that can modify the Drawable is displayed, such as
 *     a battery level or progress level.  Some drawables may modify their
 *     imagery based on the current level.
 *
 *     <li> A Drawable can perform animations by calling back to its client
 *     through the {@link Callback} interface.  All clients should support this
 *     interface (via {@link #setCallback}) so that animations will work.  A
 *     simple way to do this is through the system facilities such as
 *     {@link android.view.View#setBackgroundDrawable(Drawable)} and
 *     {@link android.widget.ImageView}.
 * </ul>
 *
 * Though usually not visible to the application, Drawables may take a variety
 * of forms:
 *
 * <ul>
 *     <li> <b>Bitmap</b>: the simplest Drawable, a PNG or JPEG image.
 *     <li> <b>Nine Patch</b>: an extension to the PNG format allows it to
 *     specify information about how to stretch it and place things inside of
 *     it.
 *     <li> <b>Shape</b>: contains simple drawing commands instead of a raw
 *     bitmap, allowing it to resize better in some cases.
 *     <li> <b>Layers</b>: a compound drawable, which draws multiple underlying
 *     drawables on top of each other.
 *     <li> <b>States</b>: a compound drawable that selects one of a set of
 *     drawables based on its state.
 *     <li> <b>Levels</b>: a compound drawable that selects one of a set of
 *     drawables based on its level.
 *     <li> <b>Scale</b>: a compound drawable with a single child drawable,
 *     whose overall size is modified based on the current level.
 * </ul>
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For more information about how to use drawables, read the
 * <a href="{@docRoot}guide/topics/graphics/2d-graphics.html">Canvas and Drawables</a> developer
 * guide. For information and examples of creating drawable resources (XML or bitmap files that
 * can be loaded in code), read the
 * <a href="{@docRoot}guide/topics/resources/drawable-resource.html">Drawable Resources</a>
 * document.</p></div>
 */
public abstract class Drawable extends ImageIcon
{
   
    
    private static final Rect ZERO_BOUNDS_RECT = new Rect();

    private int mLevel = 0;
    private int mChangingConfigurations = 0;
    private Rect mBounds = ZERO_BOUNDS_RECT;  // lazily becomes a new Rect()
    private WeakReference<Callback> mCallback = null;
    private boolean mVisible = true;

    public Drawable(String path) 
    {
        super(path);
    }

    public Drawable(BufferedImage image) 
    {
        super(image);
    }

    public Drawable(ImageIcon image) 
    {
        super(image.getImage());
    }

    /**
     * Draw in its bounds (set via setBounds) respecting optional effects such
     * as alpha (set via setAlpha) and color filter (set via setColorFilter).
     *
     * @param canvas The canvas to draw into
     */
    public abstract void draw(Canvas canvas);

    /**
     * Specify a bounding rectangle for the Drawable. This is where the drawable
     * will draw when its draw() method is called.
     */
    public void setBounds(int left, int top, int right, int bottom) {
        Rect oldBounds = mBounds;

        if (oldBounds == ZERO_BOUNDS_RECT) {
            oldBounds = mBounds = new Rect();
        }

        if (oldBounds.left != left || oldBounds.top != top ||
                oldBounds.right != right || oldBounds.bottom != bottom) {
            mBounds.set(left, top, right, bottom);
            onBoundsChange(mBounds);
        }
    }

    /**
     * Specify a bounding rectangle for the Drawable. This is where the drawable
     * will draw when its draw() method is called.
     */
    public void setBounds(Rect bounds) {
        setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    /**
     * Return a copy of the drawable's bounds in the specified Rect (allocated
     * by the caller). The bounds specify where this will draw when its draw()
     * method is called.
     *
     * @param bounds Rect to receive the drawable's bounds (allocated by the
     *               caller).
     */
    public final void copyBounds(Rect bounds) {
        bounds.set(mBounds);
    }

    /**
     * Return a copy of the drawable's bounds in a new Rect. This returns the
     * same values as getBounds(), but the returned object is guaranteed to not
     * be changed later by the drawable (i.e. it retains no reference to this
     * rect). If the caller already has a Rect allocated, call copyBounds(rect).
     *
     * @return A copy of the drawable's bounds
     */
    public final Rect copyBounds() {
        return new Rect(mBounds);
    }

    
    
    /**
     * Return the drawable's bounds Rect. Note: for efficiency, the returned
     * object may be the same object stored in the drawable (though this is not
     * guaranteed), so if a persistent copy of the bounds is needed, call
     * copyBounds(rect) instead.
     * You should also not change the object returned by this method as it may
     * be the same object stored in the drawable.
     *
     * @return The bounds of the drawable (which may change later, so caller
     *         beware). DO NOT ALTER the returned object as it may change the
     *         stored bounds of this drawable.
     *
     * @see #copyBounds()
     * @see #copyBounds(android.graphics.Rect) 
     */
    public final Rect getBounds() {
        if (mBounds == ZERO_BOUNDS_RECT) {
            mBounds = new Rect();
        }

        return mBounds;
    }
    
    
    
    /**
     * Return the intrinsic width of the underlying drawable object.  Returns
     * -1 if it has no intrinsic width, such as with a solid color.
     */
    public int getIntrinsicWidth() {
        return -1;
    }

    /**
     * Return the intrinsic height of the underlying drawable object. Returns
     * -1 if it has no intrinsic height, such as with a solid color.
     */
    public int getIntrinsicHeight() {
        return -1;
    }
    
    
    


    /**
     * Set a mask of the configuration parameters for which this drawable
     * may change, requiring that it be re-created.
     *
     * @param configs A mask of the changing configuration parameters, as
     * defined by {@link android.content.res.Configuration}.
     *
     * @see android.content.res.Configuration
     */
    public void setChangingConfigurations(int configs) {
        mChangingConfigurations = configs;
    }

    /**
     * Return a mask of the configuration parameters for which this drawable
     * may change, requiring that it be re-created.  The default implementation
     * returns whatever was provided through
     * {@link #setChangingConfigurations(int)} or 0 by default.  Subclasses
     * may extend this to or in the changing configurations of any other
     * drawables they hold.
     *
     * @return Returns a mask of the changing configuration parameters, as
     * defined by {@link android.content.res.Configuration}.
     *
     * @see android.content.res.Configuration
     */
    public int getChangingConfigurations() {
        return mChangingConfigurations;
    }

    /**
     * Set to true to have the drawable dither its colors when drawn to a device
     * with fewer than 8-bits per color component. This can improve the look on
     * those devices, but can also slow down the drawing a little.
     */
    public void setDither(boolean dither) {}

    /**
     * Set to true to have the drawable filter its bitmap when scaled or rotated
     * (for drawables that use bitmaps). If the drawable does not use bitmaps,
     * this call is ignored. This can improve the look when scaled or rotated,
     * but also slows down the drawing.
     */
    public void setFilterBitmap(boolean filter) {}

    /**
     * Implement this interface if you want to create an animated drawable that
     * extends {@link android.graphics.drawable.Drawable Drawable}.
     * Upon retrieving a drawable, use
     * {@link Drawable#setCallback(android.graphics.drawable.Drawable.Callback)}
     * to supply your implementation of the interface to the drawable; it uses
     * this interface to schedule and execute animation changes.
     */
    public static interface Callback {
        /**
         * Called when the drawable needs to be redrawn.  A view at this point
         * should invalidate itself (or at least the part of itself where the
         * drawable appears).
         *
         * @param who The drawable that is requesting the update.
         */
        public void invalidateDrawable(Drawable who);

        /**
         * A Drawable can call this to schedule the next frame of its
         * animation.  An implementation can generally simply call
         * {@link android.os.Handler#postAtTime(Runnable, Object, long)} with
         * the parameters <var>(what, who, when)</var> to perform the
         * scheduling.
         *
         * @param who The drawable being scheduled.
         * @param what The action to execute.
         * @param when The time (in milliseconds) to run.  The timebase is
         *             {@link android.os.SystemClock#uptimeMillis}
         */
        public void scheduleDrawable(Drawable who, Runnable what, long when);

        /**
         * A Drawable can call this to unschedule an action previously
         * scheduled with {@link #scheduleDrawable}.  An implementation can
         * generally simply call
         * {@link android.os.Handler#removeCallbacks(Runnable, Object)} with
         * the parameters <var>(what, who)</var> to unschedule the drawable.
         *
         * @param who The drawable being unscheduled.
         * @param what The action being unscheduled.
         */
        public void unscheduleDrawable(Drawable who, Runnable what);
    }

    /**
     * Implement this interface if you want to create an drawable that is RTL aware
     *
     * @hide
     */
    public static interface Callback2 extends Callback {
        /**
         * A Drawable can call this to get the resolved layout direction of the <var>who</var>.
         *
         * @param who The drawable being queried.
         */
        public int getResolvedLayoutDirection(Drawable who);
    }

    /**
     * Bind a {@link Callback} object to this Drawable.  Required for clients
     * that want to support animated drawables.
     *
     * @param cb The client's Callback implementation.
     * 
     * @see #getCallback() 
     */
    public final void setCallback(Callback cb) {
        mCallback = new WeakReference<Callback>(cb);
    }

    /**
     * Return the current {@link Callback} implementation attached to this
     * Drawable.
     * 
     * @return A {@link Callback} instance or null if no callback was set.
     * 
     * @see #setCallback(android.graphics.drawable.Drawable.Callback) 
     */
    public Callback getCallback() {
        if (mCallback != null) {
            return mCallback.get();
        }
        return null;
    }
    
    /**
     * Use the current {@link Callback} implementation to have this Drawable
     * redrawn.  Does nothing if there is no Callback attached to the
     * Drawable.
     *
     * @see Callback#invalidateDrawable
     * @see #getCallback() 
     * @see #setCallback(android.graphics.drawable.Drawable.Callback) 
     */
    public void invalidateSelf() {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    /**
     * Use the current {@link Callback} implementation to have this Drawable
     * scheduled.  Does nothing if there is no Callback attached to the
     * Drawable.
     *
     * @param what The action being scheduled.
     * @param when The time (in milliseconds) to run.
     *
     * @see Callback#scheduleDrawable
     */
    public void scheduleSelf(Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    /**
     * Use the current {@link Callback} implementation to have this Drawable
     * unscheduled.  Does nothing if there is no Callback attached to the
     * Drawable.
     *
     * @param what The runnable that you no longer want called.
     *
     * @see Callback#unscheduleDrawable
     */
    public void unscheduleSelf(Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }


    /**
     * Specify an alpha value for the drawable. 0 means fully transparent, and
     * 255 means fully opaque.
     */
    public abstract void setAlpha(int alpha);


    /**
     * Indicates whether this view will change its appearance based on state.
     * Clients can use this to determine whether it is necessary to calculate
     * their state and call setState.
     *
     * @return True if this view changes its appearance based on state, false
     *         otherwise.
     *
     * @see #setState(int[])
     */
    public boolean isStateful() {
        return false;
    }




    /**
     * If this Drawable does transition animations between states, ask that
     * it immediately jump to the current state and skip any active animations.
     */
    public void jumpToCurrentState() {
    }

    /**
     * @return The current drawable that will be used by this drawable. For simple drawables, this
     *         is just the drawable itself. For drawables that change state like
     *         {@link StateListDrawable} and {@link LevelListDrawable} this will be the child drawable
     *         currently in use.
     */
    public Drawable getCurrent() {
        return this;
    }

    /**
     * Specify the level for the drawable.  This allows a drawable to vary its
     * imagery based on a continuous controller, for example to show progress
     * or volume level.
     *
     * <p>If the new level you are supplying causes the appearance of the
     * Drawable to change, then it is responsible for calling
     * {@link #invalidateSelf} in order to have itself redrawn, <em>and</em>
     * true will be returned from this function.
     *
     * @param level The new level, from 0 (minimum) to 10000 (maximum).
     *
     * @return Returns true if this change in level has caused the appearance
     * of the Drawable to change (hence requiring an invalidate), otherwise
     * returns false.
     */
    public final boolean setLevel(int level) {
        if (mLevel != level) {
            mLevel = level;
            return onLevelChange(level);
        }
        return false;
    }

    /**
     * Retrieve the current level.
     *
     * @return int Current level, from 0 (minimum) to 10000 (maximum).
     */
    public final int getLevel() {
        return mLevel;
    }

    /**
     * Set whether this Drawable is visible.  This generally does not impact
     * the Drawable's behavior, but is a hint that can be used by some
     * Drawables, for example, to decide whether run animations.
     *
     * @param visible Set to true if visible, false if not.
     * @param restart You can supply true here to force the drawable to behave
     *                as if it has just become visible, even if it had last
     *                been set visible.  Used for example to force animations
     *                to restart.
     *
     * @return boolean Returns true if the new visibility is different than
     *         its previous state.
     */
    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = mVisible != visible;
        if (changed) {
            mVisible = visible;
            invalidateSelf();
        }
        return changed;
    }

    public final boolean isVisible() {
        return mVisible;
    }

    /**
     * Return the opacity/transparency of this Drawable.  The returned value is
     * one of the abstract format constants in
     * {@link android.graphics.PixelFormat}:
     * {@link android.graphics.PixelFormat#UNKNOWN},
     * {@link android.graphics.PixelFormat#TRANSLUCENT},
     * {@link android.graphics.PixelFormat#TRANSPARENT}, or
     * {@link android.graphics.PixelFormat#OPAQUE}.
     *
     * <p>Generally a Drawable should be as conservative as possible with the
     * value it returns.  For example, if it contains multiple child drawables
     * and only shows one of them at a time, if only one of the children is
     * TRANSLUCENT and the others are OPAQUE then TRANSLUCENT should be
     * returned.  You can use the method {@link #resolveOpacity} to perform a
     * standard reduction of two opacities to the appropriate single output.
     *
     * <p>Note that the returned value does <em>not</em> take into account a
     * custom alpha or color filter that has been applied by the client through
     * the {@link #setAlpha} or {@link #setColorFilter} methods.
     *
     * @return int The opacity class of the Drawable.
     *
     * @see android.graphics.PixelFormat
     */
    public abstract int getOpacity();



    /**
     * Override this in your subclass to change appearance if you recognize the
     * specified state.
     *
     * @return Returns true if the state change has caused the appearance of
     * the Drawable to change (that is, it needs to be drawn), else false
     * if it looks the same and there is no need to redraw it since its
     * last state.
     */
    protected boolean onStateChange(int[] state) { return false; }
    /** Override this in your subclass to change appearance if you vary based
     *  on level.
     * @return Returns true if the level change has caused the appearance of
     * the Drawable to change (that is, it needs to be drawn), else false
     * if it looks the same and there is no need to redraw it since its
     * last level.
     */
    protected boolean onLevelChange(int level) { return false; }
    /**
     * Override this in your subclass to change appearance if you recognize the
     * specified state.
     */
    protected void onBoundsChange(Rect bounds) {}




    /**
     * Return in padding the insets suggested by this Drawable for placing
     * content inside the drawable's bounds. Positive values move toward the
     * center of the Drawable (set Rect.inset). Returns true if this drawable
     * actually has a padding, else false. When false is returned, the padding
     * is always set to 0.
     */
    public boolean getPadding(Rect padding) {
        padding.set(0, 0, 0, 0);
        return false;
    }

    /**
     * Make this drawable mutable. This operation cannot be reversed. A mutable
     * drawable is guaranteed to not share its state with any other drawable.
     * This is especially useful when you need to modify properties of drawables
     * loaded from resources. By default, all drawables instances loaded from
     * the same resource share a common state; if you modify the state of one
     * instance, all the other instances will receive the same modification.
     *
     * Calling this method on a mutable Drawable will have no effect.
     *
     * @return This drawable.
     * @see ConstantState
     * @see #getConstantState()
     */
    public Drawable mutate() {
        return this;
    }


    /**
     * Create a drawable from file path name.
     */
    public static Drawable createFromPath(String pathName) 
    {
        ImageIcon image = new ImageIcon(pathName);
        return (Drawable) image;
    }




    /**
     * This abstract class is used by {@link Drawable}s to store shared constant state and data
     * between Drawables. {@link BitmapDrawable}s created from the same resource will for instance
     * share a unique bitmap stored in their ConstantState.
     *
     * <p>
     * {@link #newDrawable(Resources)} can be used as a factory to create new Drawable instances
     * from this ConstantState.
     * </p>
     *
     * Use {@link Drawable#getConstantState()} to retrieve the ConstantState of a Drawable. Calling
     * {@link Drawable#mutate()} on a Drawable should typically create a new ConstantState for that
     * Drawable.
     */
    public static abstract class ConstantState {
        /**
         * Create a new drawable without supplying resources the caller
         * is running in.  Note that using this means the density-dependent
         * drawables (like bitmaps) will not be able to update their target
         * density correctly. One should use {@link #newDrawable(Resources)}
         * instead to provide a resource.
         */
        public abstract Drawable newDrawable();
        /**
         * Create a new Drawable instance from its constant state.  This
         * must be implemented for drawables that change based on the target
         * density of their caller (that is depending on whether it is
         * in compatibility mode).
         */
        public Drawable newDrawable(Resources res) {
            return newDrawable();
        }
        /**
         * Return a bit mask of configuration changes that will impact
         * this drawable (and thus require completely reloading it).
         */
        public abstract int getChangingConfigurations();
    }

    /**
     * Return a {@link ConstantState} instance that holds the shared state of this Drawable.
     *q
     * @return The ConstantState associated to that Drawable.
     * @see ConstantState
     * @see Drawable#mutate()
     */
    public ConstantState getConstantState() {
        return null;
    }


}
