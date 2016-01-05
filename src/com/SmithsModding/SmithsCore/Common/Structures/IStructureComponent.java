/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.SmithsCore.Common.Structures;

import com.SmithsModding.SmithsCore.Common.PathFinding.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;

import java.util.*;

public interface IStructureComponent extends IPathComponent {

    String getStructureTypeUniqueID ();

    Cube getStructureBoundingBox ();

    boolean countsAsConnectingComponent ();

    IStructureData getStructureData ();


    void initiateAsMasterEntity ();

    void initiateAsSlaveEntity (Coordinate3D masterLocation);


    ArrayList<Coordinate3D> getSlaveCoordinates ();

    void setSlaveCoordinates (ArrayList<Coordinate3D> newSlaveCoordinates);

    void registerNewSlave (Coordinate3D newSlaveLocation);

    void removeSlave (Coordinate3D slaveLocation);


    boolean isSlaved ();

    float getDistanceToMasterEntity ();

    Coordinate3D getMasterLocation ();

    void setMasterLocation (Coordinate3D newMasterLocation);
}
