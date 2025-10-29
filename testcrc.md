# CRC Cards — Event Lottery System App (Android-style)

Each class lists Responsibilities ↔ Collaborators in one concise table.

---

## MainActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Launch app entry point and global navigation | EventListActivity, EntrantActivity, OrganizerActivity |
| Request runtime permissions (camera, location, notifications) | GeolocationService, QRCodeService, NotificationController |
| Route results from scanners and pickers | QRCodeActivity, ImageStorageController |
| Initialize global singletons/services | GeolocationService, NotificationController |

---

## EventListActivity (Event Browser)
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Display and refresh list of events | EventController |
| Apply filters and sorting | FilterController |
| Show availability windows and distance | ScheduleController, GeolocationService |
| Navigate to EventActivity for details | EventActivity |

---

## EventActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Display event details, poster, capacity | EventController, ImageStorageController |
| Show registration window state | ScheduleController |
| Join or view waiting list | WaitingListController |
| Show location/map and distance | GeolocationService |
| Launch QR view or scan flow | QRCodeActivity |

---

## EventController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Load/save event data | EventDB |
| Validate registration window | ScheduleController |
| Update poster references | ImageStorageController |
| Provide event data to UI screens | EventListActivity, EventActivity |

---

## EventDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| CRUD for events in Firestore | EventDBConnector |
| Query events by filters/sorts | FilterController |
| Provide snapshots/streams | EventController |
| Remove events on admin action | AdministratorController |

---

## EventDBConnector
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Connect to Firebase Firestore (events) | EventDB |
| Handle async reads/writes | EventController |
| Map documents to domain objects | EventDB |

---

## FilterController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Hold user filter prefs (category, time, distance) | EventListActivity |
| Build Firestore/Room queries | EventDB, GeolocationService |
| Reset/clear filters | EventListActivity |

---

## ScheduleController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Evaluate if registration is open/closed | EventController |
| Compute countdown/remaining time | EventActivity |
| Validate schedule edits | OrganizerController |

---

## EntrantActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Display and edit entrant profile | EntrantController |
| Show joined events and invites | WaitingListController, InviteController |
| Manage notification and privacy settings | NotificationController, AuthenticationService |

---

## EntrantController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Load/save entrant profile | ProfileDB |
| Join/leave waiting lists on behalf of user | WaitingListController |
| Accept/decline invites | InviteController |
| Update device binding | AuthenticationService |

---

## ProfileDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| CRUD for entrant profiles | EntrantController |
| Sync profile changes | EntrantActivity |
| Store notification preferences | NotificationController |

---

## OrganizerActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Create/edit events and posters | OrganizerController, ImageStorageController |
| Configure registration window and rules | ScheduleController |
| Run selections and notify winners | LotteryService, NotificationController |
| View waiting list and geomap | WaitingListController, GeolocationService |

---

## OrganizerController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Persist organizer event changes | EventDB |
| Enforce capacity/geofence settings | WaitingListController, GeolocationService |
| Trigger selection rounds | LotteryService |
| Broadcast organizer messages | NotificationController |

---

## AdministratorActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Moderate events, users, and images | AdministratorController |
| Review notification logs | NotificationLogDB |
| Enforce policy actions | PolicyController |

---

## AdministratorController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Remove events and profiles | EventDB, ProfileDB |
| Remove images for policy violations | ImageStorageController |
| Audit notifications | NotificationLogDB |
| Present and enforce policy | PolicyController |

---

## WaitingListActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Show waiting list size and entrants | WaitingListController |
| Allow join/leave actions | EntrantController |
| Show location map of joins | GeolocationService |

---

## WaitingListController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Add/remove entrants from list | EntrantController |
| Enforce optional capacity limits | OrganizerController |
| Provide pool to selection rounds | LotteryService |
| Persist list updates | WaitingListDB |
| Provide size and membership to UIs | EventActivity, WaitingListActivity |

---

## WaitingListDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| CRUD for waiting list entries | WaitingListController |
| Store geolocation snapshots | GeolocationService |
| Stream list changes to UI | WaitingListController |

---

## SelectionRoundController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Represent one selection round | LotteryService |
| Track selected and replacement entrants | WaitingListController |
| Persist round outcomes | RegistrationDB |
| Notify winners and declines | NotificationController, InviteController |

---

## InviteActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Show invite status and expiry | InviteController |
| Accept or decline invite | EntrantController |
| Navigate to registration on accept | RegistrationActivity |

---

## InviteController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Create and update invites | InviteDB |
| Validate expiry and state transitions | SelectionRoundController |
| Notify entrants of changes | NotificationController |
| Reflect status in registration | RegistrationController |

---

## InviteDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| CRUD for invites | InviteController |
| Index by event and entrant | RegistrationController |
| Purge expired invites | AdministratorController |

---

## RegistrationActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Display confirmed registrants | RegistrationController |
| Handle cancellations by entrants | EntrantController |
| Export final roster | CSVExportController |

---

## RegistrationController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Confirm accepted invites into roster | InviteController |
| Process cancellations and replacements | SelectionRoundController |
| Persist roster and status | RegistrationDB |
| Provide exportable lists | CSVExportController |

---

## RegistrationDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Store final enrollment per event | RegistrationController |
| Track cancellations and replacements | SelectionRoundController |
| Provide queries for exports | CSVExportController |

---

## LotteryService
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Randomly sample entrants for count | WaitingListController, SelectionRoundController |
| Log selection outcomes | NotificationLogDB |
| Trigger winner notifications | NotificationController |

---

## NotificationController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Send winner and not-chosen notifications | EntrantController, OrganizerController |
| Respect opt-out preferences | ProfileDB |
| Record delivery metadata | NotificationLogDB |

---

## NotificationLogDB
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Store notification audit logs | NotificationController |
| Provide logs for admin review | AdministratorController |

---

## QRCodeActivity
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Display event QR for sharing | QRCodeService, EventController |
| Scan a QR and return result | QRCodeService, MainActivity |
| Route to event details or join flow | EventActivity, WaitingListActivity |

---

## QRCodeService
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Generate QR images for event data | EventController, ImageStorageController |
| Decode scanned payload to domain | EventController |
| Validate and sanitize payload | PolicyController |

---

## GeolocationService
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Capture entrant join locations (runtime permission) | EntrantController, WaitingListController |
| Verify organizer geofence settings | OrganizerController, EventController |
| Provide distance and mapping utilities | EventListActivity, EventActivity |
| Render simple map bitmaps if needed | EventActivity |

---

## AuthenticationService
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Provide unique device identifier | EntrantController |
| Bind and unbind device to account | EntrantController, AdministratorController |
| Support secure rebind on logout | EntrantActivity |

---

## ImageStorageController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Upload/update/delete posters | OrganizerController, AdministratorController |
| Return poster URLs to event pages | EventController |
| Moderate removals on policy actions | AdministratorController, PolicyController |

---

## CSVExportController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Export final enrolled list to CSV | RegistrationController |
| Provide file handle/URI to UI | RegistrationActivity |

---

## PolicyController
| **Responsibilities** | **Collaborator(s)** |
|---|---|
| Store and present policy text | AdministratorActivity |
| Flag suspected policy violations | AdministratorController |
| Validate inbound data (basic checks) | QRCodeService, ImageStorageController |

