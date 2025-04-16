% Genius - Music Management System 🎵
* A comprehensive Java application for music artists, fans, and administrators to manage songs, albums, and lyric collaborations.

% Table of Contents
* Features

* User Roles

* Regular User

* Artist

* Admin

* Technical Implementation

* Data Persistence

* Serialization

% Future Improvements %

* Features
🎶 Core Functionality
* User registration and authentication system

* Role-based access control (User/Artist/Admin)

* Complete music management system

* Interactive CLI interface with rich formatting

💾 Data Management
* File-based persistence using Java Serialization

* Automatic data saving after modifications

* Data integrity checks and validation

* Backup system for critical data

🎨 Rich Interactions
* Song lyric editing and version control

* Comment system for songs

* Artist following system

* Approval workflow for artist accounts

% User Roles
Regular User 👤
Browse Music:

* View all available songs

* Sort by popularity, release date, or artist

* Search for specific artists or songs

% Interact with Content:

* Add comments to songs

* Submit lyric edit suggestions

* View song statistics (plays, edits)

% Social Features:

* Follow favorite artists

* View music from followed artists

* View artist profiles

Artist 🎤
% All Regular User features plus:

% Music Management:

* Create and edit songs

* Organize songs into albums

* Manage song lyrics

& Collaboration:

* Review lyric edit suggestions from users

* Approve/reject lyric changes

* View edit history

% Statistics:

* View play counts for songs

* See most popular songs

* Track follower count

Admin 👑
% All Artist features plus:

% User Management:

* Approve/reject artist applications

* View all registered users

* Manage user roles

% Content Moderation:

* Review all lyric edit requests

* Remove inappropriate content

* Manage reported issues

* System Maintenance:

* Backup and restore data

* View system statistics

* Manage database integrity

% Technical Implementation
% Data Persistence
% File-based storage using .dat files:

accounts.dat - User/Artist accounts

songs.dat - Song catalog

albums.dat - Album collections

pending_artists.dat - Artist applications

Automatic loading on startup

Periodic saving after modifications

% Serialization
Java Object Serialization:

All model classes implement Serializable

Custom serialVersionUID for version control

Proper handling of serialization exceptions

Data Structure:

Lists stored as serialized collections

Relationships maintained through usernames/IDs

Atomic counters for ID generation

How to Run
Requirements:
Java JDK 23
Gradle (for building)

Build and Run:
System will create necessary data files
Default admin account created automatically

Sample data can be initialized (optional)

* Future Improvements
* Planned Features
% JSON Migration:

Transition from Java serialization to JSON

Better cross-platform compatibility

Human-readable data files

Enhanced Features:

Playlist functionality

Song ratings system

Advanced search filters

Technical Upgrades:

Password hashing (BCrypt)

Activity logging

Automated backups

Architecture Improvements
Database abstraction layer

Dependency injection

Proper exception hierarchy

Comprehensive unit tests
