/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.levelmechanics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;
import java.util.List;

public class BoundedContactListener implements ContactListener{

    private boolean isTouchingBlackHoleCenter;
    private boolean isTouchingKey;
    private int numPortalContacts;
    private int numGumCloudContacts;
    private boolean isTouchingLaser;

    private Body ball = null;
    private List<Body> allArrows;
    private List<Body> allBlackHoles;
    private List<Body> allFans;
    private List<Body> allMagnets;
    private List<Body> allTeleporters;
    private Body body = null;

    // Vector2 variable for velocity of the ball before it touches the teleporter
    private Vector2 tmpVelocity;

    public BoundedContactListener() {
        allArrows = new ArrayList<Body>();
        allBlackHoles = new ArrayList<Body>();
        allFans = new ArrayList<Body>();
        allMagnets = new ArrayList<Body>();
        allTeleporters = new ArrayList<Body>();
    }

    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) { return; }

        // will run if one of the fixtures contains a ball
        if ((fa.getUserData() != null && fa.getUserData().equals("ball")) ||
                (fb.getUserData() != null && fb.getUserData().equals("ball"))) {

            // Arrow
            if (fa.getUserData() != null && fa.getUserData().equals("arrow")) {
                allArrows.add(fa.getBody());
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("arrow")) {
                allArrows.add(fb.getBody());
                ball = fa.getBody();
            }

            // Black Hole
            if (fa.getUserData() != null && fa.getUserData().equals("blackHole")) {
                allBlackHoles.add(fa.getBody());
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("blackHole")) {
                allBlackHoles.add(fb.getBody());
                ball = fa.getBody();
            }

            // Black Hole Center
            if (fa.getUserData() != null && fa.getUserData().equals("blackHoleCenter")) {
                isTouchingBlackHoleCenter = true;
            } else if (fb.getUserData() != null && fb.getUserData().equals("blackHoleCenter")) {
                isTouchingBlackHoleCenter = true;
            }

            // Fan
            if (fa.getUserData() != null && fa.getUserData().equals("fan")) {
                allFans.add(fa.getBody());
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("fan")) {
                allFans.add(fb.getBody());
                ball = fa.getBody();
            }

            // Gum Cloud
            if (fa.getUserData() != null && fa.getUserData().equals("gumCloud")) {
                numGumCloudContacts++;
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("gumCloud")) {
                numGumCloudContacts++;
                ball = fa.getBody();
            }

            // Key
            if (fa.getUserData() != null && fa.getUserData().equals("key")) {
                isTouchingKey = true;
                body = fa.getBody();
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("key")) {
                isTouchingKey = true;
                body = fb.getBody();
                ball = fa.getBody();
            }

            // Magnet
            if (fa.getUserData() != null && fa.getUserData().equals("magnet")) {
                allMagnets.add(fa.getBody());
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("magnet")) {
                allMagnets.add(fb.getBody());
                ball = fa.getBody();
            }

            // Portal
            if (fa.getUserData() != null && fa.getUserData().equals("portal")) {
                numPortalContacts++;
                body = fa.getBody();
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("portal")) {
                numPortalContacts++;
                body = fb.getBody();
                ball = fa.getBody();
            }

            // Laser
            if (fa.getUserData() != null && fa.getUserData().equals("laser")) {
                isTouchingLaser = true;
                ball = fb.getBody();
            } else if (fb.getUserData() != null && fb.getUserData().equals("laser")) {
                isTouchingLaser = true;
                ball = fa.getBody();
            }
        }
    }
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) { return; }

        if ((fa.getUserData() != null && fa.getUserData().equals("ball")) ||
                (fb.getUserData() != null && fb.getUserData().equals("ball"))) {

            body = null;
            ball = null;

            if (fa.getUserData() != null && fa.getUserData().equals("arrow")) {
                allArrows.remove(fa.getBody());
            } else if (fb.getUserData() != null && fb.getUserData().equals("arrow")) {
                allArrows.remove(fb.getBody());
            }

            if (fa.getUserData() != null && fa.getUserData().equals("blackHole")) {
                allBlackHoles.remove(fa.getBody());
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("blackHole")) {
                allBlackHoles.remove(fb.getBody());
            }

            if (fa.getUserData() != null && fa.getUserData().equals("blackHoleCenter")) {
                isTouchingBlackHoleCenter = false;
            } else if (fb.getUserData() != null && fb.getUserData().equals("blackHoleCenter")) {
                isTouchingBlackHoleCenter = false;
            }

            if (fa.getUserData() != null && fa.getUserData().equals("fan")) {
                allFans.remove(fa.getBody());
            } else if (fb.getUserData() != null && fb.getUserData().equals("fan")) {
                allFans.remove(fb.getBody());
            }

            if (fa.getUserData() != null && fa.getUserData().equals("key")) {
                isTouchingKey = false;
            } else if (fb.getUserData() != null && fb.getUserData().equals("key")) {
                isTouchingKey = false;
            }

            if (fa.getUserData() != null && fa.getUserData().equals("magnet")) {
                allMagnets.remove(fa.getBody());
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("magnet")) {
                allMagnets.remove(fb.getBody());
            }

            if (fa.getUserData() != null && fa.getUserData().equals("gumCloud")) {
                numGumCloudContacts--;
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("gumCloud")) {
                numGumCloudContacts--;
            }

            if (fa.getUserData() != null && fa.getUserData().equals("portal")) {
                numPortalContacts--;
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("portal")) {
                numPortalContacts--;
            }

            if (fa.getUserData() != null && fa.getUserData().equals("teleporter")) {
                allTeleporters.remove(fa.getBody());
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("teleporter")) {
                allTeleporters.remove(fb.getBody());
            }

            if (fa.getUserData() != null && fa.getUserData().equals("laser")) {
                isTouchingLaser = false;
            }
            else if (fb.getUserData() != null && fb.getUserData().equals("laser")) {
                isTouchingLaser = false;
            }

        }

    }

    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa == null || fb == null) { return; }

        // Teleporter
        if (fa.getUserData() != null && fa.getUserData().equals("teleporter")) {
            allTeleporters.add(fa.getBody());
            tmpVelocity = fb.getBody().getLinearVelocity();
        } else if (fb.getUserData() != null && fb.getUserData().equals("teleporter")) {
            allTeleporters.add(fb.getBody());
            tmpVelocity = fa.getBody().getLinearVelocity();
        }
    }
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    // Getters
    public boolean isTouchingArrow() { return !allArrows.isEmpty(); }
    public boolean isTouchingBlackHole() { return !allBlackHoles.isEmpty(); }
    public boolean isTouchingBlackHoleCenter() { return isTouchingBlackHoleCenter; }
    public boolean isTouchingFan() { return !allFans.isEmpty(); }
    public boolean isTouchingGumCloud () { return numGumCloudContacts > 0; }
    public boolean isTouchingKey() { return isTouchingKey; }
    public boolean isTouchingMagnet() { return !allMagnets.isEmpty(); }
    public boolean isTouchingPortal() { return numPortalContacts > 0; }
    public boolean isTouchingTeleporter() { return !allTeleporters.isEmpty(); }
    public boolean isTouchingLaser() {return isTouchingLaser;}

    public List<Body> getAllArrows() { return allArrows; }
    public List<Body> getAllBlackHoles() { return allBlackHoles; }
    public List<Body> getAllFans() { return allFans; }
    public List<Body> getAllMagnets() { return allMagnets; }
    public List<Body> getAllTeleporters() { return allTeleporters; }
    public Body getBody() { return body; }
    public Body getBall() { return ball; }

    public Vector2 getTmpVelocity() {
        Vector2 tmp = tmpVelocity;
        tmpVelocity = null;
        return tmp;
    }

    // Setters
    public void resetContacts() {
        allArrows = new ArrayList<Body>();
        allBlackHoles = new ArrayList<Body>();
        allFans = new ArrayList<Body>();
        allMagnets = new ArrayList<Body>();
        allTeleporters = new ArrayList<Body>();

        isTouchingBlackHoleCenter = false;
        isTouchingKey = false;
        isTouchingLaser = false;
    }

}
