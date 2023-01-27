/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package poly;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus, the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
@SuppressWarnings("JdkObsolete")
public final class RatPolyStack implements Iterable<RatPoly> {

    /**
     * Stack containing the RatPoly objects.
     */
    private final Stack<RatPoly> polys;

    // Abstraction Function:
    // AF(this) = A LIFO stack where the top of this.polys is the top of this,
    // and the bottom of this.polys is the bottom of this (with the elements in between
    // in insertion order, the newest closer to the top).
    //
    // RepInvariant:
    // polys != null &&
    // forall i such that (0 <= i < polys.size(), polys.get(i) != null

    /**
     * Constructs a new RatPolyStack.
     *
     * @spec.effects Constructs a new RatPolyStack, [].
     */
    public RatPolyStack() {
        this.polys = new Stack<RatPoly>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.polys != null) : "polys should never be null.";

        for(RatPoly p : this.polys) {
            assert (p != null) : "polys should never contain a null element.";
        }
    }

    /**
     * Returns the number of RatPolys in this RatPolyStack.
     *
     * @return the size of this sequence.
     */
    public int size() {
    	return this.polys.size();
    }

    /**
     * Pushes a RatPoly onto the top of this.
     *
     * @param p the RatPoly to push onto this stack
     * @spec.requires p != null
     * @spec.modifies this
     * @spec.effects this_post = [p]:this.
     */
    public void push(RatPoly p) {
        checkRep(); // make sure in consistent state
        if (p != null) {
            this.polys.push(p);
        } else {
            return;
        }
        checkRep(); // will be called every time push method is invoked
    }

    /**
     * Removes and returns the top RatPoly.
     *
     * @return p where this = [p]:S.
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = S.
     */
    public RatPoly pop() {
        checkRep(); // make sure in consistent state
        RatPoly pop = new RatPoly();
        if (this.size() > 0) {
            pop = this.polys.pop();
        }
        return pop;
    }

    /**
     * Duplicates the top RatPoly on this.
     *
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = [p, p]:S.
     */
    public void dup() {
        checkRep(); // make sure in consistent state
        if (this.size() > 0) {
            this.polys.push(this.polys.peek());
        } else {
            return;
        }
        checkRep(); // will be called every time dup method is invoked
    }

    /**
     * Swaps the top two elements of this.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S.
     */
    public void swap() {
        checkRep(); // make sure in consistent state
        if (this.size() >= 2) {
            RatPoly p1 = this.polys.pop();
            RatPoly p2 = this.polys.pop();
            this.polys.push(p1);
            this.polys.push(p2);
        } else {
            return;
        }
        checkRep(); // will be called every time swap method is invoked
    }

    /**
     * Clears the stack.
     *
     * @spec.modifies this
     * @spec.effects this_post = [].
     */
    public void clear() {
        checkRep(); // make sure in consistent state
        this.polys.clear();
        checkRep(); // will be called every time clear method is invoked
    }

    /**
     * Returns the RatPoly that is 'index' elements from the top of the stack.
     *
     * @param index the index of the RatPoly to be retrieved
     * @return if this = S:[p]:T where S.size() = index, then returns p.
     * @spec.requires {@code index >= 0 && index < this.size()}
     */
    public RatPoly getNthFromTop(int index) {
        checkRep(); // make sure in consistent state
        if (index >= 0 && index < this.size()) {
            Stack<RatPoly> temp = new Stack<>();
            // push into the temp stack and pop from polys until the nth from top term
            for (int i = 0; i < index; i++) {
                temp.push(this.polys.pop());
            }
            // Save a copy iof the nth from top term
            RatPoly p = this.polys.peek();
            // push the items in temp stack back into polys
            while (!temp.empty()) {
                this.polys.push(temp.pop());
            }
            checkRep(); // will be called every time this method is invoked
            // return the nth from top term
            return p;
        } else {
            throw new IllegalArgumentException("Index out of range");
        }
    }

    /**
     * Pops two elements off of the stack, adds them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2.
     */
    public void add() {
        checkRep(); // make sure in consistent state
        if (this.size() >= 2) {
            RatPoly rp1 = this.polys.pop();
            RatPoly rp2 = this.polys.pop();
            this.polys.push(rp1.add(rp2));
        } else {
            return;
        }
        checkRep(); // will be called every time add method is invoked
    }

    /**
     * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1.
     */
    public void sub() {
        checkRep(); // make sure in consistent state
        if (this.size() >= 2) {
            RatPoly rp1 = this.polys.pop();
            RatPoly rp2 = this.polys.pop();
            this.polys.push(rp2.sub(rp1));
        } else {
            return;
        }
        checkRep(); // will be called every time sub method is invoked
    }

    /**
     * Pops two elements off of the stack, multiplies them, and places the result on top of the
     * stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2.
     */
    public void mul() {
        checkRep(); // make sure in consistent state
        if (this.size() >= 2) {
            RatPoly rp1 = this.polys.pop();
            RatPoly rp2 = this.polys.pop();
            this.polys.push(rp1.mul(rp2));
        } else {
            return;
        }
        checkRep(); // will be called every time mul method is invoked
    }

    /**
     * Divides the next from top poly by the top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1.
     */
    public void div() {
        checkRep(); // make sure in consistent state
        if (this.size() >= 2) {
            RatPoly rp1 = this.polys.pop();
            RatPoly rp2 = this.polys.pop();
            this.polys.push(rp2.div(rp1));
        } else {
            return;
        }
        checkRep(); // will be called every time div method is invoked
    }

    /**
     * Returns an iterator of the elements contained in the stack.
     *
     * @return an iterator of the elements contained in the stack in order from the bottom of the
     * stack to the top of the stack.
     */
    @Override
    public Iterator<RatPoly> iterator() {
        return this.polys.iterator();
    }
}
